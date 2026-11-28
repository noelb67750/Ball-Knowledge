import json
import pandas as pd
import sys


def load_dataframe(file_path):
    """
    Loads a dataframe from a file and converts it to a pandas dataframe
    Also adds a Taken column.
    :param file_path: Directory to load the dataframe from.
    :return: Pandas DataFrame
    """

    if file_path.endswith(".csv"):
        df = pd.read_csv(file_path)

    elif file_path.endswith(".tsv"):
        df = pd.read_csv(file_path, sep="\t")

    elif file_path.endswith(".xlsx"):
        df = pd.read_excel(file_path)

    else:
        raise Exception("Unsupported file type")

    #For each column based off type fill missing values
    for column in df.columns:
        # Check if the column is any numeric type (int, float, etc.)
        if (column == "ID"):
            df[column] = df[column].fillna(-1)
        if (pd.api.types.is_numeric_dtype(df[column]) and column != "ID"):
            df[column] = df[column].fillna(0)
        if (df[column].dtype == "object"):
            df[column] = df[column].fillna("NA")

    df["Taken"] = False
    return df

def calculate_ratings(df):
    #Turns all the stats into an array
    stats = ['MP', 'FG', 'FGA', 'FG%',
             '3P', '3PA', '3P%', '2P', '2PA', '2P%', 'eFG%', 'FT', 'FTA', 'FT%',
             'ORB', 'DRB', 'TRB', 'AST', 'STL', 'BLK', 'TOV', 'PF', 'PTS']
    positions = ['PG', 'SG', 'SF', 'PF', 'C']

    #For each stat get a percentile score based on a player's position
    for stat in stats:
        df[f'{stat}_pct'] = df.groupby('Pos')[stat].rank(pct=True)

    #Inverse the percentile score for turn over and personal fouls since the lower, the better
    df['TOV_pct'] = df.groupby('Pos')['TOV'].rank(pct=True, ascending=False)
    df['PF_pct'] = df.groupby('Pos')['PF'].rank(pct=True, ascending=False)

    #AI crafted formulas for each position
    df.loc[df['Pos'] == 'PG', 'OVR_Score'] = (
            0.25 * df['AST_pct']
            + 0.20 * df['PTS_pct']
            + 0.15 * df['3P%_pct']
            + 0.15 * df['3P_pct']
            + 0.10 * df['STL_pct']
            + 0.05 * df['eFG%_pct']
            + 0.05 * df['FT%_pct']
            + 0.03 * df['TOV_pct']
            + 0.02 * df['MP_pct']
    )

    df.loc[df['Pos'] == 'SG', 'OVR_Score'] = (
            0.30 * df['PTS_pct']
            + 0.20 * df['3P_pct']
            + 0.15 * df['3P%_pct']
            + 0.10 * df['eFG%_pct']
            + 0.10 * df['AST_pct']
            + 0.05 * df['STL_pct']
            + 0.05 * df['FT%_pct']
            + 0.03 * df['TOV_pct']
            + 0.02 * df['MP_pct']
    )

    df.loc[df['Pos'] == 'SF', 'OVR_Score'] = (
            0.20 * df['PTS_pct']
            + 0.15 * df['TRB_pct']
            + 0.15 * df['AST_pct']
            + 0.10 * df['STL_pct']
            + 0.10 * df['BLK_pct']
            + 0.10 * df['3P%_pct']
            + 0.10 * df['eFG%_pct']
            + 0.05 * df['FT%_pct']
            + 0.05 * df['MP_pct']
    )

    df.loc[df['Pos'] == 'PF', 'OVR_Score'] = (
            0.20 * df['PTS_pct']
            + 0.25 * df['TRB_pct']
            + 0.15 * df['ORB_pct']
            + 0.10 * df['BLK_pct']
            + 0.15 * df['eFG%_pct']
            + 0.05 * df['AST_pct']
            + 0.05 * df['STL_pct']
            + 0.05 * df['MP_pct']
    )

    df.loc[df['Pos'] == 'C', 'OVR_Score'] = (
            0.15 * df['PTS_pct']
            + 0.25 * df['TRB_pct']
            + 0.15 * df['ORB_pct']
            + 0.20 * df['BLK_pct']
            + 0.10 * df['eFG%_pct']
            + 0.02 * df['AST_pct']
            + 0.05 * df['DRB_pct']
            + 0.03 * df['MP_pct']
    )

    df.loc[~df['Pos'].isin(positions), 'OVR_Score'] = 0

    #Get the minimum and maximum overall score
    min_score = df['OVR_Score'].min()
    max_score = df['OVR_Score'].max()

    #Scaling to resemble video games like NBA 2K
    #After a quick google search the lowest rating possible is 40 with the highest being 99
    #That is why 40 is down below
    #The 59 is here to reach up to a max of 99
    #Then in the parenthesis scales the overall score between 0 and 1, so it can then be multiplied by the 59
    #After that we curve with a power of 0.75 to create a more realistic distribution instead of clustering
    #The last rounds and saves the new score as an int
    df['OVR'] = (40 + 59 *((df['OVR_Score'] - min_score) /(max_score - min_score))**0.75).round().astype(int)

    return df

file_path = sys.argv[1] #Saves the file path from Java

df = calculate_ratings(load_dataframe(file_path)) #Calls the methods above together

output_file = "current_dataframe.csv" #Stores the name of the output file as a string

df.to_csv(output_file, index=False) #Writes the dataframe without creating an extra row and column

print(output_file)
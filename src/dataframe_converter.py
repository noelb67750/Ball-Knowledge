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
    df["Taken"] = False
    return df

file_path = sys.argv[1] #Saves the file path from Java

df = load_dataframe(file_path) # Calls the method above

output_file = "current_dataframe.csv" #Stores the name of the output file as a string

df.to_csv(output_file, index=False) #Writes the dataframe without creating an extra row and column

print(output_file)
package ballknowledge;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * Json front end sends to create a draft
 * 
 * for example {"teams": 3, "budget": 500000000, "rounds": 10}
 */

//record becuase the objects only intention is to carry values from the front end to the back end
@JsonIgnoreProperties(ignoreUnknown = true) //the server ignores extra fields instead of crashing
public record DraftRequest(int teams, double budget) {}

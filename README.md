# Page-Rank-Algorithm-Implementation

The dataset for this project will is a graph that shows connections between various airports. This data is available at the 'Bureau of Transportation' website.

The following fields are considered to create the graph.

 Reporting Airline
 Origin (Origin Airport Code)
 OriginCityName
 Dest (Destination Airport Code)
 DestCityName

PageRank algorithm can be used to rank nodes in a graph in order of their importance.

Computed the page rank of each node (airport) based on number of inlinks and outlinks.
There may be multiple connections between two airports, and it is considered independent of each other to compute the number of inlinks and outlinks. For example, if node A is connected to node B with an out-count of 10 and node C with an out-count of 10, then the total number of outlinks for node A would be 20.


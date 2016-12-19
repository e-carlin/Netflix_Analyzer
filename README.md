This was a project for CS361 Algortihms and Data Structures.
The application takes Netflix data on movies and reviews, builds a
directed graph using the data, and then is able to perform analysis
on the graph.

I worked with one other classmate on this project. I personally implemented
option number 1 for building the graph, the Floyd Warhsall algorithm, diameter,
and average length of the shortest path statistics.

There are two options for building the graph:
1) Movie u and v are adjacent if they were made within 5 years of eachother.
2) Movie u and v are adjacent if atleast one person has seen both of them.

Some of the graph analysis you can run:
- Number of nodes
- Number of edges
- Density
- Maximum degree of any node
- Diameter, computed using the Floyd Warshall algorithm
- Average length of the shortest paths, computed using Floyd Warhsall
- Display shortes path between any two nodes, computed using Dijkstra's Algorithm

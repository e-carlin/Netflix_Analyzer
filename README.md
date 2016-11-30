Options to build graph:
1) u and v are adjacent if they were made within 5 years of eachother
2) u and v are adjacent it atleast one user has watched both movies

Add Nodes{
list movies = FileProcessor.getMovies();
for(i=0; i<movies.size(); i++){
g.addNode(list(i).getMovieID);
}

Movie find(int id){
    for all movies
        if(movies.get(i).getID() == ID){
        return movies.get(i);
        }
}

BuildGraph Option1{
for int i=0;i<movies.size(); i++){
    Movie u = movies.get(i);
    for(int j=0; j<movies.size(); j++){
        if(movie(i) == movie(j) continue; //node can't be connected to itself
        Movie v = movies.get(i);
        if(Math.abs(u.getYear - v.getYear) <5){
        //add edge between u and v
        g.addEdge(u.getID(), v.getID());

}
}
}
}

BuildGraph option2{
for int i=0;i<movies.size(); i++){
    Movie u = movies.get(i);
    for(int j=0; j<movies.size(); j++){
        if(movie(i) == movie(j) continue; //node can't be connected to itself
        Movie v = movies.get(i);
for(int k = 0; k=reviewers.size(); k++){
Reviewer r = reviewers.get(k);
if(r.keySet().contains(u.getID()) && r.keySet().contains(v.getID())){
g.addEdge(u.getID, v.getID);
break;
}
}
        //add edge between u and v
        g.addEdge(u.getID(), v.getID());
}
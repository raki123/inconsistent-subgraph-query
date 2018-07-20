# inconsistent-subgraph-query
A starting point for querying the laundromat HDT for inconsistent subgraphs.
Run ./install-dependencies.sh to install the required jars to your local repository.
The size of the inconsistent dataset can be configured by tuning the parameters

    public static int classCountMax = 100;
    public static int propertyCountMax = 50;
    public static int listCountMax = 100;
    public static int classAssCountMax = 100;
    public static int relationAssCountMax = 100;

in ./src/main/java/org/defaults/preferential/App.java and the recursion depth in the same file on line 196: 

    getClassRelations(search, hdt, 3, known)

change the 3 to a higher value to go further.
Results will be written to test*.nt.

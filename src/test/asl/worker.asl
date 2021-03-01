


+!init(NThreads) : NThreads > 1 =>
    N = "thread" + ((#executionContext.name.replaceAll("thread","").toInt % NThreads) + 1);
    +neighbor(N).

+!token(0) =>
    #coms.achieve("master", done).

+!token(T) : neighbor(N) =>
    #coms.achieve(N, token(T - 1)).


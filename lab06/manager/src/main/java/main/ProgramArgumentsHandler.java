package main;

public class ProgramArgumentsHandler {
    private String[] programArguments;
    private Integer port;
    private String host;
    private String rmiName;
    private Integer managerObjectPort;

    public ProgramArgumentsHandler(String[] args) throws Exception {
        this.programArguments = args;
        processArgs();
    }

    private void processArgs() throws Exception {
        try{
            for(int i = 0; i < programArguments.length; i++){
                if(!programArguments[i].startsWith("-"))
                    continue;
                i++;
                switch (programArguments[i - 1]){
                    case "-h":
                    case "--host":
                        host = programArguments[i];
                        break;
                    case "-p":
                    case "--port":
                        port = Integer.parseInt(programArguments[i]);
                        break;
                    case "-n":
                    case "--name":
                        rmiName = programArguments[i];
                        break;
                    case "--manager-port":
                        managerObjectPort = Integer.parseInt(programArguments[i]);
                        break;
                }
            }
        } catch (NumberFormatException | IndexOutOfBoundsException e){
            throw new Exception("Invalid command line arguments! Shutting down...");
        }

    }

    public Integer getPort() {
        return port;
    }

    public String getHost() {
        return host;
    }

    public String getRmiName() {
        return rmiName;
    }

    public Integer getManagerObjectPort() {
        return managerObjectPort;
    }
}

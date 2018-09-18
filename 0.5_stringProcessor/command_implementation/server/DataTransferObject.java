package StringProcessor.server;




public class DataTransferObject{

  public DataTransferObject(){

  }

  private String command;

  private String dataToEdit;

  public String getCommand(){
    return command;
  }
  public String getDataToEdit(){
    return dataToEdit;
  }
  public void setCommand(String mCommand){
    command = mCommand;
  }
  public void setDataToEdit(String mDataToEdit){
    dataToEdit = mDataToEdit;
  }

}

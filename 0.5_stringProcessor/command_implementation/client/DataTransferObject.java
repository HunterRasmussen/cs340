package StringProcessor.client;




public class DataTransferObject{

  public DataTransferObject(String mCommand, String mDataToEdit){
    command = mCommand;
    dataToEdit = mDataToEdit;
  }

  private String command;

  private String dataToEdit;


  public String getCommand(){
    return command;
  }
  public String getDataToEdit(){
    return dataToEdit;
  }

}

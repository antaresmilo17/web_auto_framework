package com.tigertext.automation.commonUtils;
import org.springframework.stereotype.Component;
import java.io.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class FileGenerator {

    public Logger logger = Logger.getLogger(CommonMethod.class.getName());
    private final String classPathRoot = new File(FileGenerator.class.getProtectionDomain().getCodeSource().getLocation().getPath()).toString();
    private final String filePath = new File(classPathRoot).getParentFile().getParentFile().getParentFile().toString();
    private String fileName = "";
    private File file;

    public void createFile(String filename)
    {
        fileName = filename;
        try {
            file = new File(filePath,"/" + fileName);
            boolean fvar = file.createNewFile();
            if (fvar){
                logger.log(Level.INFO, "File has been created successfully in path: " + filePath);
            }
            else{
                logger.log(Level.INFO, "File already present at the specified location");
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Exception Occurred: "+ e.getStackTrace());
        }
    }

    public void writeToFile(List<String> textToWrite )
    {
        try(FileWriter fw = new FileWriter(getFilename(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println(textToWrite);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Exception Occurred: "+ e.getStackTrace());
        }

    }

    public void readFile() throws IOException
    {
        BufferedReader br = new BufferedReader(new FileReader(file));

        String st;
        while ((st = br.readLine()) != null) {
            String test = st.replaceAll("[*.]","");
            logger.log(Level.INFO, test);
        }

    }

    public void clearFile(String fileName) throws IOException
    {
        logger.log(Level.INFO, "Clearing File: " + fileName);

        PrintWriter pw = new PrintWriter(filePath + "/" +fileName);
        pw.close();

    }

    public void deleteFile(String fileName)
    {
        try{
            File file = new File(filePath + "/" +fileName);
            if(file.delete()){
                System.out.println(file.getName() + " is deleted!");
            }else{
                System.out.println("Delete failed: File didn't delete");
            }
        }catch(Exception e){
            logger.log(Level.SEVERE, "Exception Occurred: "+ e.getStackTrace());
        }

    }

    public String getFilePath() {return filePath;}
    public String getFilename() {return fileName;}
    public File getFile(){return file;}
}

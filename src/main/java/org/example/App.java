package org.example;

import org.alicebot.ab.*;
import org.alicebot.ab.utils.IOUtils;

import java.io.File;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final boolean TRACE_MODE = true;
    static String botName = "super";

    public static void main( String[] args )
    {
        try{
            String resourcePath = getResourcePath();
            MagicBooleans.trace_mode = TRACE_MODE;
            Bot bot = new Bot("super", resourcePath);
//            bot.writeAIMLFiles();
            Chat chatSession = new Chat(bot);
            bot.brain.nodeStats();
            String textLine = "";
            while(true){
                System.out.println("Human: ");
                textLine = IOUtils.readInputTextLine();
                if(textLine == null || textLine.length()<1){
                    textLine = MagicStrings.null_input;
                }
                if(textLine.equals("q")){
                    System.exit(0);
                }else if(textLine.equals("wq")){
                    bot.writeQuit();
                    System.exit(0);
                }else{
                    String request = textLine;
                    if(MagicBooleans.trace_mode){
                        System.out.println("STATE = "+request+ ":THAT="+((History) chatSession.thatHistory.get(0)).get(0)+":TOPIC="+ chatSession.predicates.get("topic"));
                        String response = chatSession.multisentenceRespond(request);
                        while(response.contains("&lt;"))
                            response = response.replace("&lt;","<");
                        while(response.contains("&gt;"))
                            response = response.replace("&gt;",">");
                        System.out.println("Robot: "+response);
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static String getResourcePath(){
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        path = path.substring(0, path.length() - 2);
        System.out.println(path);
        String resourcesPath = path + File.separator + "src" + File.separator + "main" + File.separator + "resources";
        return resourcesPath;
    }
}

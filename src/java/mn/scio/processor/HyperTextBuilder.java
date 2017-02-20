package mn.scio.processor;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lupino
 */
public class HyperTextBuilder {
    
    private String templateName;
    private List<String> lines = new ArrayList<String>(); // index file
    private Path file;
    
    private boolean isTagClosed = true;
    private int tabIndentLevel = 2;
    private String tab = "   ";
    
    public HyperTextBuilder(String templateName){
        this.templateName = templateName;
    }
    
    public void initTemplate(){
        // Template directory with unique id
        String templateDir = "hyperTextBuilder/" + 
                this.templateName + "_" + System.currentTimeMillis();
        
        // Bootstrap directory
        String libBootstrap = "hyperTextLib/bootstrap";
        
        // Create subdirectory and boostrap files
        new File(templateDir+"/css").mkdirs();
        new File(templateDir+"/js").mkdirs();
        new File(templateDir+"/img").mkdirs();
        try {
            copyFile(new File(libBootstrap+"/css/bootstrap.min.css"), 
                    new File(templateDir+"/css/bootstrap.min.css"));
            copyFile(new File(libBootstrap+"/css/bootstrap-theme.min.css"), 
                    new File(templateDir+"/css/bootstrap-theme.min.css"));
            copyFile(new File(libBootstrap+"/js/bootstrap.min.js"), 
                    new File(templateDir+"/js/bootstrap.min.js"));
            copyFile(new File(libBootstrap+"/css/style.css"), 
                    new File(templateDir+"/css/style.css"));
        } catch (IOException ex) {
            Logger.getLogger(HyperTextBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.file = Paths.get(templateDir+"/index.html");
        
        // Construct HTML
        setHeader();
        setBody();
        setFooter();
    }
    
    private void setBody(){
        
    }
    
    private void setHeader(){
        List<String> head = Arrays.asList(
                "<!DOCTYPE html>", 
                "<html>",
                "   <head>",
                "       <meta charset='UTF-8'>",
                "       <meta author='Khronometer'>",
                "       <link href=\"css/bootstrap.min.css\" rel=\"stylesheet\" type=\"text/css\"/>",
                "       <link href=\"css/bootstrap-theme.min.css\" rel=\"stylesheet\" type=\"text/css\"/>",
                "       <link href=\"css/style.css\" rel=\"stylesheet\" type=\"text/css\"/>",
                "       <script src=\"js/bootstrap.min.js\" type=\"text/javascript\"></script>",
                "       <title>"+this.templateName+"</title>",
                "   </head>",
                "   <body>",
                "       <div class\"bdy\"'>"
        );
        lines.addAll(head);
    }
    
    private void setFooter(){
        List<String> foot = Arrays.asList(
                "       </div>",
                "   </body>", 
                "</html>"
        );
        lines.addAll(foot);
    }
    
    // Write all lines to index file
    public void finalizeTemplate(){
        try {
            Files.write(file, lines, Charset.forName("UTF-8"));
        } catch (IOException ex) {
            Logger.getLogger(HyperTextBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void copyFile(File from, File to) throws IOException {
        Files.copy(from.toPath(), to.toPath());
    }
    
}
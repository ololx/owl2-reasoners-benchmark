package owl2reasonersbenchmark;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The type HTML Logger.
 *
 * @author Alexander A. Kropotin
 * @project owl2-reasoners-benchmark
 * @created 14.05.2014 09:43 <p>
 */
public class HTMLLogger {
    private String path;
    private FileWriter log;
    
    protected void finalize () throws Throwable {
        try {
            this.write("</table>");
            closeLog();
            this.log = null;
            this.path = null;
        } finally {
            super.finalize();
        }
    }
    
    public HTMLLogger () {
        
    }
    public HTMLLogger (String path) throws IOException {
        setPath (path);
        setLog (new File (this.path));
        this.write("<table border = '0'>");
    }
    public void setPath (String path) {
        this.path = path;
    }
    private void setLog (File file) throws IOException {
        this.log = new FileWriter(file);
    }
    public void closeLog () throws IOException {
        this.log.close ();
    }
    public void write (String text) throws IOException {
        this.log.append(text);
    }
    public void write (String type, String text) throws IOException {
        String color;
        switch (type.toUpperCase()) {
            case "INFO":
                color = "Grey";
                break;
            case "ALLERT":
                color = "Red";
                break;
            case "ATTENTION":
                color = "Yellow";
                break;
            default:
                color = "Grey";
                break;
        }
        this.log.append("<tr bgcolor = '"+color+"'><td><b>"+text+"</b></td></tr>");
        System.out.println (type.toUpperCase()+":"+text);
    }
}

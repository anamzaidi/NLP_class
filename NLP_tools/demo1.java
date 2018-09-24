// Stanford CoreNLP Demo1
// Print annotations

import java.util.*;
import edu.stanford.nlp.io.*;
import edu.stanford.nlp.pipeline.*;

/* You need to add the following jar files as external jar files to your project,
    or add all the CoreNLP jar files to your classpath
    stanford-corenlp-3.9.1.jar
    stanford-corenlp-3.9.1-models.jar
    joda-time.jar
    jollyday.jar
 */

public class demo1 {
    public static void main(String[] args)  {

        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse");

        Annotation annotation;
        // either use default text or read in a file
        if (args.length > 0) {
            annotation = new Annotation(IOUtils.slurpFileNoExceptions(args[0]));
            System.out.println("Getting text from input file");
        } else {
            annotation = new Annotation("All happy families are alike. Every unhappy" +
                    " family is unhappy in its own way");
        }

        // run the pipeline to create the annotations
        System.out.println("Starting CoreNLP");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        pipeline.annotate(annotation);

        // print to console
        pipeline.prettyPrint(annotation, System.out);

        System.out.println("End of program");
    }
}

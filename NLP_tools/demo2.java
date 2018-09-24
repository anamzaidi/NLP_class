// Stanford CoreNLP Demo2
// extracting individual annotation components

import java.util.*;

import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.io.*;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.util.*;

/* You need to add the following jar files as external jar files to your project
    stanford-corenlp-3.9.1.jar
    stanford-corenlp-3.9.1-models.jar
    joda-time.jar
    jollyday.jar
 */

public class demo2 {
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

        // you can also access the annotations in the program
        System.out.println("Extracting elements from the annotations");
        System.out.println();

        // print tokens
        System.out.println("Tokens");
        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap s : sentences) {
            System.out.println(s);
            System.out.println();
            for (CoreMap token : s.get(CoreAnnotations.TokensAnnotation.class)) {
                System.out.println(token.toShorterString());

                // print tree
                System.out.println();
                System.out.println("Tree:");
                Tree tree = s.get(TreeCoreAnnotations.TreeAnnotation.class);
                tree.pennPrint(System.out);

                // print dependencies
                System.out.println();
                System.out.println("Dependencies");
                System.out.println(s.get(
                        SemanticGraphCoreAnnotations.BasicDependenciesAnnotation.class).toString(
                        SemanticGraph.OutputFormat.LIST));

            }
        }

        System.out.println("End of program");
    }
}

package examples.simple;

import br.com.luvia.Luvia;
import br.com.luvia.core.context.ApplicationGL;

public class SimpleExamples extends Luvia {

    public SimpleExamples() {
        //super(640,480);
        super(1024, 576);
    }

    // Main program
    public static void main(String[] args) {
        SimpleExamples engine = new SimpleExamples();
        engine.setTitle("Luvia Examples");
        engine.setIcon("cross_blue.png");
        engine.init();
    }

    @Override
    public ApplicationGL startApplication() {

        initialSetup("");

        return new ConeDrawing(w, h);
    }

}

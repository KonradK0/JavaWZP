import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.junit.Test;

public class OptionsTest {

    Options options = new Options();

    @Test
    public void option(){
        options.addOption(OptionBuilder.withArgName("customerId").hasArg().withDescription("customer id").create("customerId"));
        if(options.hasOption("customerId")){
            options.getOption("customerId").getValuesList().forEach(System.out::println);
        }
    }
}

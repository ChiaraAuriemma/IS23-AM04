package it.polimi.it.view;

import org.junit.Before;
import org.junit.Test;

public class TestCli {
    private View testView;
    private final String border = "║";
    private final String noPlayer = "           ";


    @Before
    public void View(){
        this.testView = new View();
    }

    @Test
    public void basic(){
        System.out.println(testView.firstLine);
        System.out.println(border + "   " + "JackB1233mmm" + "   " + "JackB1233mmm" + "   " + "JackB1233mmm" + "   " + "JackB1233mmm" + "   " + border);
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
        System.out.println ("\u001B[43m" + "  vmkvjgck                  " +
                "\u001B[0m" + " altra roba");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");

        System.out.println ("\u001B[43m  " + " "+"\u001B[43m" + " " + "\u001B[44m" + "  " + "\u001B[0m" + "  "+ "\u001B[0m" + "  "+ "\u001B[45m" + "  " + "\u001B[0m" + "  \n");
        System.out.println ("\u001B[43m" + "  " + "\u001B[44m" + "  " + "\u001B[45m" + "  " + "\u001B[0m" + "  ");
    }
}

package it.minoranza.minorgroup.minordealer.control;

import it.minoranza.minorgroup.minorclient.control.AppendObjectOutputStream;
import it.minoranza.minorgroup.minorclient.model.Auto;

import java.io.*;
import java.util.ArrayList;

public final class GestoreFile {

    private static final String ext = ".dat";

    public static void inserimento(final Auto t, final List list) throws Exception {

        final FileOutputStream fOUT;
        final ObjectOutputStream oOUT;

        final File file = new File(list + ext);

        if (!file.exists()) {
            fOUT = new FileOutputStream(file);
            oOUT = new ObjectOutputStream(fOUT);
        } else {
            fOUT = new FileOutputStream(file, true);
            oOUT = new AppendObjectOutputStream(fOUT);
        }

        oOUT.writeObject(t);

        oOUT.flush();
        oOUT.close();
        fOUT.close();

    }

    public static ArrayList<Auto> read(final List list) throws Exception {
        final ArrayList<Auto> auto = new ArrayList<>();
        //System.out.println("hello" + ext);

        final File file = new File(list + ext);
        if (file.exists()) {
            final FileInputStream fIN = new FileInputStream(file);
            final ObjectInputStream oIN = new ObjectInputStream(fIN);

            while (true) {
                try {
                    System.out.println("just here");
                    auto.add((Auto) oIN.readObject());
                } catch (Exception exc) {
                    System.err.println("end reading");
                    break;
                }
            }

            oIN.close();
            fIN.close();
        }

        return auto;
    }

    public static boolean delete(final Auto auto, final List list) throws Exception {
        final File diApp = new File("appoggio" + ext);
        final File orig = new File(list + ext);

        final FileOutputStream fOUT = new FileOutputStream(diApp);
        final ObjectOutputStream oOUT = new ObjectOutputStream(fOUT);
        boolean b = false;

        final FileInputStream fIN = new FileInputStream(orig);
        final ObjectInputStream oIN = new ObjectInputStream(fIN);

        while (true) {
            try {
                final Auto letta = (Auto) oIN.readObject();
                if (letta.equals(auto))
                    b = true;
                else
                    oOUT.writeObject(letta);
            } catch (Exception exc) {
                break;
            }
        }

        oOUT.close();
        fOUT.close();
        oIN.close();
        fIN.close();

        orig.delete();
        diApp.renameTo(orig);

        return b;
    }

    public enum List {
        onSale,
        sold;

        @Override
        public String toString() {
            return "_" + super.toString();
        }
    }

}

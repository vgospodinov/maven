package com.initech.ini.maven.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.zip.CRC32;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Utilities zum Bearbeiten von .zip Archiven.
 * <p>
 * CAVE: das Ding hier ist sehr speziell und ohen Anpassungen für
 * nichts anderes zu gebrauchen
 *
 *
 * @author andyman
 */
public class Zip {


//    private static final int BUFFER_SIZE_WRITE = 4096;

    private static final int BUFFER_SIZE_DECOMPRESS = 8192;

    /**
     * Ziemlich spezielle Methode, die ein Archiv erwartet, dass
     * genau eine Datei enthält. Diese Datei wird dann nicht unter
     * ihrem eigentlichen Namen entpackt, sondern unter dem Namen
     * des Archivs, dem lediglich die .zip Endung weggestrippt wird.
     * <p>
     * CAVE: nur für den Interhome-Job zu gebrauchen...
     *
     * @param inFile
     * @param outFile
     * @throws IOException
     *
     */
    public static final void decompress(File inFile, File outFile)
            throws IOException {

        checkInfile(inFile);
        checkOutFile(outFile);

        // Create a buffered gzip input stream to the archive file.
        ZipInputStream zipInputStream;
        try {
            FileInputStream in = new FileInputStream(inFile);
            BufferedInputStream source = new BufferedInputStream(in);
            zipInputStream = new ZipInputStream(source);

            // Get the first entry
            //
            @SuppressWarnings("unused")
            ZipEntry entry = zipInputStream.getNextEntry();

            byte[] inputBuffer = new byte[BUFFER_SIZE_DECOMPRESS];
            int len = 0;
            // Create a buffered output stream to the file.
            FileOutputStream out = new FileOutputStream(outFile);
            BufferedOutputStream destination =
                new BufferedOutputStream(out, BUFFER_SIZE_DECOMPRESS);

            // Now read from the gzip stream, which will decompress the data,
            // and write to the output stream.
            while ((len = zipInputStream.read(inputBuffer, 0, BUFFER_SIZE_DECOMPRESS)) != -1) {
                destination.write(inputBuffer, 0, len);
            }

            destination.flush(); // Insure that all data is written to the
                                 // output.
            out.close();


        } catch (IOException e) {
            throw e;
        }



        try {
            zipInputStream.close();
        } catch (IOException e) {
        }

    }


    /**
     *
     *
     * @param archive
     * @param targetDirectory
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public static void decompressAll(File archive, File targetDirectory) throws IOException {


        try {
            ZipFile zipFile = new ZipFile(archive);
            Enumeration enumeration = zipFile.entries();

            while (enumeration.hasMoreElements()) {
                ZipEntry zipEntry = (ZipEntry) enumeration.nextElement();

                BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(zipEntry));

                int size;
                byte[] buffer = new byte[2048];

                FileOutputStream fos = new FileOutputStream(
                        new File(targetDirectory, zipEntry.getName()));
                BufferedOutputStream bos = new BufferedOutputStream(fos, buffer.length);

                while ((size = bis.read(buffer, 0, buffer.length)) != -1) {
                    bos.write(buffer, 0, size);
                }

                bos.flush();
                bos.close();
                fos.close();

                bis.close();
            }
        } catch (IOException e) {
            String err = MessageFormat.format(
                "Kann Datei nicht erfolgreich entpacken: ''{0}''",
                archive.getAbsolutePath());
            throw new IOException(err, e);
        }

    }


    /**
     * Not yet implemented
     * <p>
     *
     * @param inFile
     * @param outFile
     * @throws IOException
     */
    public static final void compress(File inFile, File outFile)
            throws IOException {

        checkInfile(inFile);
        checkOutFile(outFile);

//        String[] source = new String[]{  inFile.getAbsolutePath()  };
        
        // Create a buffer for reading the files
        byte[] buf = new byte[1024];
        
        try {
            // Create the ZIP file
//            String target = "target.zip";
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(outFile));
        
            // Compress the files
//            for (int i=0; i<source.length; i++) {
                FileInputStream in = new FileInputStream(inFile);
        
                // Add ZIP entry to output stream.
                out.putNextEntry(new ZipEntry(inFile.getName()));
        
                // Transfer bytes from the file to the ZIP file
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
        
                // Complete the entry
                out.closeEntry();
                in.close();
//            }
        
            // Complete the ZIP file
            out.close();
        } catch (IOException e) {
        }

    }

    /**
     * Hilfsmethode. Überprüft, ob das outFile, das geschrieben werden soll, bereits
     * vorhanden ist. Falls ja, dann wird die IOException geworfen.
     * @param outFile
     * @throws IOException
     */
    private static void checkOutFile(File outFile) throws IOException {

        if (outFile == null) {
            throw new IOException("outFile ist null");
        }

        if (outFile.exists()) {
            throw new IOException("outFile '" + outFile.getAbsolutePath()
                + "' existiert bereits. Die Datei wird "
                + "nicht überschrieben!");
        }

    }

    /**
     * Hilfsmethode. für die Methoden compress() und decompress() wird der
     * inFile überprüft. Das Fileobjekt darf nicht null sein, der inFile muss
     * existieren und lesbar sein.
     *
     * @throws IOException
     *             Wenn die angegebenen Bedingungen nicht erfüllt sind.
     */
    private static void checkInfile(File inFile) throws IOException {
        IOException exception = null;
        if (inFile == null) {
            throw new IOException("inFile ist null");
        }
        if (!inFile.exists()) {
            exception = new IOException("inFile existiert nicht: '"
                    + inFile.getAbsolutePath() + "'");
        }
        if (!inFile.canRead()) {
            exception = new IOException("inFile kann nicht gelesen werden: '"
                    + inFile.getAbsolutePath());
        }

        if (exception != null) {
            throw exception;
        }
    }

}

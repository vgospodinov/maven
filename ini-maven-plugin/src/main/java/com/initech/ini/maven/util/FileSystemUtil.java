package com.initech.ini.maven.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * Eine Sammlung von Methoden, die sich mit der Zeit gesammelt haben. Weiß
 * nicht, was davon nützlich sein könnte. Möglicherweise lohnt es sich auch,
 * jakarta-commons durchzusehen, die haben ohne Ende Dateisystem-Utilities..
 *
 * @author andyman
 *
 */
public class FileSystemUtil {


    /**
     * Erzeugen eines Verzeichnisses, inkl. aller Parent-Verzeichnisse, wenn
     * diese nicht vorhanden sind.
     *
     * @param directoryName
     *            Verzeichnis zum Erzeugen
     *
     * @throws IOException
     *             falls das Verzeichnis nicht angelegt werden kann
     *
     */
    public static void mkdir(String directoryName) throws IOException {

        if (directoryName == null) {
            throw new IOException(
                            "Name des zu erzeugenden Verzeichnisses darf nicht null sein");
        }

        try {
            File file = new File(directoryName);
            file.mkdirs();
        } // /CLOVER:OFF
        // SecurityException wird nur geschmissen, wenn mit einem
        // SecurityManager
        // gearbeitet wird; dies ist nicht der Fall; dennoch muss ich diese
        // Exeption
        // abfangen; deshalb Clover off
        catch (SecurityException e) {
            throw new IOException("Kann Verzeichnis [" + directoryName
                            + "] nicht erzeugen");
        } // /CLOVER:ON
    }

    /**
     * Löscht alle Inhalte eines Verzeichnisses rekursiv, jedoch ohne das
     * Verzeichnis selbst zu löschen
     *
     * @param path
     *            Der Pfad zum Verzeichnis, das gelöscht werden soll
     * @throws IOException
     *             Wenn das Verzeichnis nicht geleert werden kann (z.Bsp. nicht
     *             vorhanden, unzureichende Schreibrechte etc.)
     */
    public static void emptyDir(String path) throws IOException {
        File dir = new File(path);
        emptyDir(dir);
    }

    /**
     * Löscht ein Verzeichnis inkl. aller Inhalte
     *
     * @param path
     *            Pfad zum Verzeichnis, das gelöscht werden soll
     * @throws IOException
     *             Wenn Löschvorgang nicht erfolgreich abgeschlossen werden
     *             konnte
     */
    public static void deleteDirectoryRecursivly(String path)
                    throws IOException {
        File file = new File(path);
        deleteDirectoryRecursivly(file);
    }

    /**
     * Löscht alle Inhalte eines Verzeichnisses rekursiv, jedoch ohne das
     * Verzeichnis selbst zu löschen
     *
     * @param dir
     *            Directory zum leeren
     * @throws IOException
     *             Wenn das Verzeichnis nicht geleert werden kann.
     */
    protected static void emptyDir(File dir) throws IOException {

        if (!(dir.exists() && dir.canWrite())) {
            throw new IOException(
                            "Kann Verzeichnis nicht leeren, es ist nicht vorhanden "
                                            + "oder nicht beschreibbar");
        }

        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                File current = new File(dir, children[i]);
                if (current.isDirectory()) {
                    deleteDirectoryRecursivly(current);
                }
                if (current.isFile()) {
                    current.delete();
                }
            }
        } else {
            throw new IOException("Kann Datei nicht leeren");
        }
    }

    /**
     * Löscht ein Verzeichnis samt Inhalte und Unterverzeichnisse
     *
     * @param dir
     *            Das zu löschende Verzeichnis
     * @throws IOException
     *             Wenn Löschvorgang nicht erfolgreich abgeschlossen werden
     *             konnte
     */
    public static void deleteDirectoryRecursivly(File dir) throws IOException {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                deleteDirectoryRecursivly(new File(dir, children[i]));
            }
        }
        dir.delete();
    }

    /**
     * Liefert alle Namen der Unterverzeichnisse, die im Verzeichnis baseDir
     * abgelegt sind, und deren Name nicht auf suffix endet
     *
     * @param baseDir
     *            das Basis-Verzeichnis, in dem gesucht wird
     *
     * @param excludeSuffix
     *            das Suffix (Filter)
     *
     * @return Set von Dateinamen, leeres Set, wenn keine Verzeichnisse gefunden
     *         wurden
     *
     *
     */
    public Set<String> getAllContainedDirectoriesWithoutSuffix(
                    final File baseDir, final String excludeSuffix) {

        Set<String> result = new HashSet<String>();

        if (baseDir == null || (!baseDir.exists()) || (!baseDir.canRead())) {
            // throw new IllegalArgumentException("baseDir ist nicht gültig");

        } else {
            String[] children;

            // Filter als anonyme innere Klasse implementieren
            FilenameFilter filter = new FilenameFilter() {

                public boolean accept(File dir, String name) {
                    File thePotentialSubdirectory = new File(dir, name);
                    return (!name.endsWith(excludeSuffix))
                                    && thePotentialSubdirectory.isDirectory();
                }
            };

            children = baseDir.list(filter);

            for (int i = 0; i < children.length; i++) {
                result.add(children[i]);
            }

        }

        return result;
    }

    /**
     * Hilfsmethode, um eine Datei zu kopieren
     *
     * @param src
     *            Die Quelldatei
     *
     * @param dst
     *            Die Zieldatei
     *
     * @throws IOException
     *             Wenn Kopiervorang nicht erfolgreich ist
     */
    public void copy(File src, File dst) throws IOException {

        InputStream in = null;
        OutputStream out = null;

        try {
            in = new FileInputStream(src);
            out = new FileOutputStream(dst);

            // Bytes transferrieren
            byte[] buf = new byte[1024];
            int len;

            for (;;) { // bisschen haesslich, aber Clover braucht das so. Siehe
                        // StyleGuidelines
                len = in.read(buf);
                if (len <= 0) {
                    break;
                } else {
                    out.write(buf, 0, len);
                }
            }

        } catch (IOException e) {
            //LOG.warn("Fehler beim Kopieren einer Datei", e);
            throw e;
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     *
     * Hilfsmethode, um die Inhalte eines Verzeichnisses in ein anderes zu
     * kopieren.
     *
     * @param srcDir
     *            Quell-Verzeichnis
     *
     * @param dstDir
     *            Ziel-Verzeichnis
     * @throws IOException
     *             Fehler beim Kopieren des Verzeichnis
     */
    public void copyFiles(String srcDir, String dstDir) throws IOException {

        String sep = File.separator;

        File fdstDir = getDirectoryAsFile(dstDir);

        if (!fdstDir.exists()) {
            fdstDir.mkdirs();
        }

        String[] fileList = getFileListOfDirectory(srcDir);

        boolean afile;

        for (int i = 0; i < fileList.length; i++) {
            afile = getDirectoryAsFile(srcDir + sep + fileList[i]).isFile();
            if (afile) {
                copy(getDirectoryAsFile(srcDir + sep + fileList[i]),
                                getDirectoryAsFile(dstDir + sep + fileList[i]));
            }
        }

    }

    // /CLOVER:OFF
    // Diese Methoden sind zu reinen Testzwecken da, um diese bei Tests zu
    // ueberschreiben und
    // Mockfiles zurueckzuliefern
    /**
     * liefert Fileobjekt aus dem gegebenen Verzeichnisnamen.
     *
     * @param dir
     *            Verzeichnis/Pfad
     * @return File
     */
    protected File getDirectoryAsFile(String dir) {
        return new File(dir);
    }

    /**
     * liefert Liste der Unterverzeichnisse bzw Files in einem Verzeichnis
     *
     * @param dir
     *            Verzeichnis/Pfad
     * @return Liste der Unterverzeichnisse/Files als Stringarray
     */
    protected String[] getFileListOfDirectory(String dir) {
        return new File(dir).list();
    }

    // /CLOVER:ON



    /**
     * helper function to create a zip-file out of an array of filenames
     */
    public void createZipFile(@SuppressWarnings("rawtypes") List filenames, String outFilename) {

        // Create a buffer for reading the files
        byte[] buf = new byte[1024];

        try {
            // Create the ZIP file
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
                            outFilename));

            // Compress the files
            for (Iterator<?> iter = filenames.iterator(); iter.hasNext();) {
                String theFile = (String) iter.next();
                File f = new File(theFile);

                FileInputStream in = new FileInputStream(f);

                // Add ZIP entry to output stream.
                // make relative pathes first
                String subdir = f.getName().equals("dump.sql") ? "db" : "scan";

                out.putNextEntry(new ZipEntry(subdir + File.separator
                                + f.getName()));

                // Transfer bytes from the file to the ZIP file
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }

                // Complete the entry
                out.closeEntry();
                in.close();
            }

            // Complete the ZIP file
            out.close();
        } catch (IOException e) {


        }
    }

    /**
     * class internal little helper function
     *
     * @param directory
     *            The directory, which should be created
     */
    public void ceateDirIfNotExists(URL directory) {
        if (directory != null) {
            // set directory only if
            File file = new File(directory.getFile());
            if (!(file.exists())) {
                file.mkdirs();
            }
        }
    }

    /** helper function to delete a directory recusivly with all it's contents */
    public boolean deleteDirectory(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return (path.delete());
    }



    /**
     * Hilfsmethode
     * @param fileToDelete
     * @throws IOException
     */
    public static void deleteFileIfExists(File fileToDelete) throws IOException {

        if(fileToDelete == null) {
            throw new IllegalArgumentException("Das übergebene File-Objekt darf nicht null sein");
        }

        if(fileToDelete.exists()){
            
        	boolean deleted = fileToDelete.delete();
            
            if(!deleted) {
                String errMsg = "das Datenfile " +
                    "[" + fileToDelete + "] wurde nicht erfolgreich gelöscht werden";
                throw new IOException(errMsg);
            }
        }
    }

    /**
     * Auslesen einer Datei in einen String
     *
     * @param file
     * @return
     */
    public static String readFile(File file) {

    	if(!file.exists()) {
    		String errorMsg = "File '" + file + "' existiert nicht";
    	}

    	if(!file.exists()) {
    		String errorMsg = "File '" + file + "' kann nicht gelesen werden nicht";
    	}

        StringBuffer result = new StringBuffer();
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            String str = null;
            while ((str = in.readLine()) != null) {
                result.append(str);
            }
        } catch (UnsupportedEncodingException e) {
        } catch (IOException e) {
        }

        return result.toString();
    }

    /**
     * @param baseDir
     * @param filenames
     * @throws IOException
     */
    public static void deleteFilesInDir(File baseDir, String[] filenames) throws IOException {

        if(baseDir == null || ! baseDir.exists()) {
            throw new IllegalArgumentException("Basedir ist null oder existiert nicht: '"
                + baseDir + "'");
        }

        for (String filename : filenames) {
            File toDelete = new File(baseDir, filename);
            try {
                deleteFileIfExists(toDelete);
            } catch (IOException e) {
                // nix weiter unternehmen
            }
        }

    }

    /**
     * @param filesToRemove
     * @throws IOException
     */
    public static void deleteFiles(File[] filesToRemove) throws IOException {
        for (File file : filesToRemove) {
            deleteFileIfExists(file);
        }
    }
    
    
    
    
    /**
     * @param filesToRemove
     * @throws IOException
     */
    public static boolean deleteFilesSilently(File[] filesToRemove){
        boolean success = true;
    	for (File file : filesToRemove) {
    		boolean tmp = file.delete();
    		
    		if(!tmp) success = false;
    		
//            try {
//            	
////				deleteFileIfExists(file);
//				
//			} catch (IOException e) {
//				LOG.warn(e);
//				success = false;
//			}
        }
    	return success;
    }
    
    
    

}

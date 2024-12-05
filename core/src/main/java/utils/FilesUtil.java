package utils;

import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FilesUtil {
    private final static Logger LOGGER = LoggerFactory.getLogger(FilesUtil.class);

    /**
     * Возвращает содержимое файла в формате String
     *
     * @param path путь к файлу
     * @return содержимое файла в формате String
     */
    @Step("Прочитать содержимое файла '{path}'")
    public static String readFileToString(String path) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String i = br.readLine();
            while (i != null) {
                sb.append(i);
                i = br.readLine();
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    /**
     * Записывает переданные в метод данные в формате String в файл по указанному пути
     *
     * @param path директория, по которой создастся файл
     * @param name имя файла
     * @param data данные для записи в файл
     * @return объект класса File
     * @throws IOException
     */
    @Step("Записать данные в файл '{path}/{name}'")
    public static File writeStringToFile(String path, String name, String data) throws IOException {
        File file = new File(path + File.separator + name);
        try (PrintWriter pw = new PrintWriter(file)) {
            pw.println(data);
            pw.flush();
        }
        return file;
    }

    /**
     * Удаляет файл/файлы в папке по указанному пути
     *
     * @param folder путь к папке
     * @param fileName имена файла(ов) в папке, подлежащих удалению
     */
    @Step("Удалить файл/файлы в папке '{path}'")
    public static void deleteFileOrFilesByNameInFolder(String folder, String ... fileName) {
        for (String file : fileName) {
            try {
                Files.delete(Path.of(folder + file));
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

    /**
     * Удаляет в папке все файлы (кроме вложенных папок) по указанному пути
     *
     * @param folder путь к папке
     */
    @Step("Удалить в папке '{path}' все файлы, кроме папок")
    public static void deleteAllFilesInFolder(String folder) {
        for (File file : new File(folder).listFiles()) {
            if (file.isFile()) file.delete();
        }
    }

    /**
     * Удаляет все содержимое в папке по указанному пути
     *
     * @param folder путь к папке
     */
    @Step("Удалить в папке '{path}' все файлы и папки")
    public static void deleteAllInFolder(String folder) {
        for (File file : new File(folder).listFiles()) {
            file.delete();
        }
    }

    /**
     * Разархивирует содержимое zip-архива в папку по указанному пути
     *
     * @param zip путь к архиву
     * @param outPath директория, в которую будет произведена разархивация
     * @return список имен разархивированных файлов (содержимое архива)
     */
    @Step("Разархивировать архив {zip} в директорию {folder}")
    public static List<String> decompressZipFile(String zip, String outPath) {
        List<String> outFileNames = new ArrayList<>();
        byte[] buffer = new byte[2048];
        try (FileInputStream fis = new FileInputStream(zip);
            BufferedInputStream bis = new BufferedInputStream(fis);
            ZipInputStream zis = new ZipInputStream(bis)) {
            ZipEntry ze;
            while ((ze = zis.getNextEntry()) != null) {
                outFileNames.add(ze.getName());
                Path filePath = Paths.get(outPath).resolve(ze.getName().replace("\"", ""));
                try (FileOutputStream fos = new FileOutputStream(filePath.toFile());
                    BufferedOutputStream bos = new BufferedOutputStream(fos, buffer.length)) {
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        bos.write(buffer, 0, len);
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return outFileNames;
    }
}

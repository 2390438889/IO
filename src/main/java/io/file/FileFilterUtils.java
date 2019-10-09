package io.file;

import java.io.File;
import java.io.FileFilter;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Hearts
 * @date 2019/10/9
 * @desc 创建文件过滤器
 */
public class FileFilterUtils {

    /**
     * 过滤所有文件类型的过滤器
     * @return
     */
    public static FileFilter createFileFilter(){
        return new FileFilter(){
            @Override
            public boolean accept(File pathname) {
                return pathname.isFile();
            }
        };
    }

    /**
     * 过滤所有文件类型的过滤器
     * @return
     */
    public static FileFilter createDictionaryFilter(){
        return new FileFilter(){
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        };
    }

    /**
     * 对文件名进行正则过滤
     * @param regex
     * @return
     */
    public static FileFilter createRegexFilter(final String regex){
        return new FileFilter(){
            private Pattern pattern = Pattern.compile(regex);
            @Override
            public boolean accept(File pathname) {
                return pattern.matcher(pathname.getName()).matches();
            }
        };
    }




    /**
     * 创建多文件过滤器
     * @param filters
     * @return
     */
    public static FileFilter createFiltersResult(List<FileFilter> filters){
        FileFilters fileFilters = new FileFilters();
        fileFilters.addFilters(filters);
        return fileFilters;
    }

    /**
     * 多文件过滤器
     */
    static class FileFilters implements  FileFilter{

        private List<FileFilter> fileFilters;

        public void clear(){
            fileFilters.clear();
        }

        public void addFilter(FileFilter fileFilter){
            fileFilters.add(fileFilter);
        }

        public void addFilters(List<FileFilter> filters){
            fileFilters.addAll(filters);
        }


        @Override
        public boolean accept(File pathname) {

            for (FileFilter fileFilter : fileFilters) {
               if (!fileFilter.accept(pathname)){
                   return false;
               }
            }

            return true;
        }
    }

}

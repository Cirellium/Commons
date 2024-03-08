package net.cirellium.commons.common.util.clazz;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;

import net.cirellium.commons.common.logger.CirelliumLogger;
import net.cirellium.commons.common.version.Platform;

public class ClassUtils {

    static Logger logger = new CirelliumLogger(Platform.getCurrentPlatform(), "ClassUtils");

    public static Collection<Class<?>> getClassesInPackage(Plugin plugin, String packageName) {
        JarFile jarFile;
        Collection<Class<?>> classes = new ArrayList<>();
        CodeSource codeSource = plugin.getClass().getProtectionDomain().getCodeSource();
        URL resource = codeSource.getLocation();
        String relPath = packageName.replace('.', '/');
        String resPath = resource.getPath().replace("%20", " ");
        String jarPath = resPath.replaceFirst("[.]jar[!].*", ".jar").replaceFirst("file:", "");
        try {
            jarFile = new JarFile(jarPath);
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException reading JAR File '" + jarPath + "'", e);
        }
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String entryName = entry.getName();
            String className = null;
            if (entryName.endsWith(".class") && entryName.startsWith(relPath)
                    && entryName.length() > relPath.length() + "/".length())
                className = entryName.replace('/', '.').replace('\\', '.').replace(".class", "");
            if (className != null) {
                Class<?> clazz = null;
                try {
                    clazz = Class.forName(className);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if (clazz != null)
                    classes.add(clazz);
            }
        }
        try {
            jarFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (Collection<Class<?>>) ImmutableSet.copyOf(classes);
    }

    public static List<Class<?>> getClasses(String packageName) throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes;
    }

    public static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(
                        Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }

    public static Set<Class<?>> getAllClasses(String packageName) throws IOException {
        return ClassPath.from(ClassLoader.getPlatformClassLoader())
                .getAllClasses()
                .stream()
                // .filter(clazz -> clazz.getPackageName().contains(packageName))
                .map(clazz -> clazz.load())
                .collect(Collectors.toSet());
    }

    public static Set<Class<?>> getAllClasses(Plugin plugin, String packageName) throws IOException {
        return ClassPath.from(plugin.getClass().getClassLoader())
                .getAllClasses()
                .stream()
                .filter(clazz -> clazz.getPackageName().contains(packageName))
                .map(clazz -> clazz.load())
                .collect(Collectors.toSet());
    }

    public static Set<? extends Class<?>> getAllClasses(String packageName, Class<?> clazz) throws IOException {
        Reflections reflections = new Reflections(packageName);

        // Disable logging of `Reflections`, which is very verbose - used code example
        // from GitHub
        // https://github.com/ronmamo/reflections/issues/266#issuecomment-878138509
        // ch.qos.logback.classic.Logger root;
        // root = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger("org.reflections");
        // root.setLevel(ch.qos.logback.classic.Level.OFF);

        // LoggerFactory.getLogger("org.reflections").

        return reflections.getSubTypesOf(clazz);
    }

    public static @Nullable String identifyClassLoader(ClassLoader classLoader) throws ReflectiveOperationException {
        Class<?> pluginClassLoaderClass = Class.forName("org.bukkit.plugin.java.PluginClassLoader");
        if (pluginClassLoaderClass.isInstance(classLoader)) {
            Method getPluginMethod = pluginClassLoaderClass.getDeclaredMethod("getPlugin");
            getPluginMethod.setAccessible(true);

            JavaPlugin plugin = (JavaPlugin) getPluginMethod.invoke(classLoader);
            return plugin.getName();
        }
        return null;
    }

}
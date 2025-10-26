package test;

import annotation.Controller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainTest {

    private static List<Class<?>> findControllerClasses(String basePackage) {
        List<Class<?>> controllers = new ArrayList<>();
        try {
            // Obtenir le chemin de base
            String basePath = "src/main/java";
            File baseDirectory = new File(basePath);
            
            // Rechercher récursivement dans tous les packages
            searchControllers(baseDirectory, "", controllers);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return controllers;
    }
    
    private static void searchControllers(File directory, String currentPackage, List<Class<?>> controllers) {
        if (!directory.exists()) {
            return;
        }
        
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // Construire le nom du package pour ce répertoire
                    String nextPackage = currentPackage.isEmpty() ? 
                        file.getName() : currentPackage + "." + file.getName();
                    // Rechercher récursivement dans ce répertoire
                    searchControllers(file, nextPackage, controllers);
                } else if (file.getName().endsWith(".java")) {
                    // Traiter le fichier Java
                    try {
                        String className = currentPackage.isEmpty() ? 
                            file.getName().replace(".java", "") :
                            currentPackage + "." + file.getName().replace(".java", "");
                        
                        // Charger la classe
                        Class<?> clazz = Class.forName(className);
                        
                        // Vérifier si la classe a l'annotation @Controller
                        if (clazz.isAnnotationPresent(Controller.class)) {
                            controllers.add(clazz);
                            System.out.println("Found controller: " + clazz.getName());
                        }
                    } catch (ClassNotFoundException e) {
                        // Ignorer les classes qui ne peuvent pas être chargées
                        System.out.println("Could not load class: " + file.getName());
                    }
                }
            }
        }
    }
    

   public static void main(String[] args) throws Exception {
    // Recherche toutes les classes avec l'annotation @Controller
    List<Class<?>> controllerClasses = findControllerClasses("");
    
    if (controllerClasses.isEmpty()) {
        System.out.println("Aucun controller trouvé");
        return;
    }

    // Afficher les informations sur chaque controller trouvé
    for (Class<?> controller : controllerClasses) {
        Controller annotation = controller.getAnnotation(Controller.class);
        System.out.println("Controller: " + controller.getName());
        System.out.println("Base path: " + annotation.value());
    }
}
}

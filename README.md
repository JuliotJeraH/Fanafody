# Recommandation de Médicaments - Java Swing (MVC)

Projet minimal en Java/Swing montrant un design MVC pour recommander des médicaments
selon les symptômes et le budget d'un patient.

- Fonctionnalités:
- Saisie des symptômes (entiers) et du budget (en Ariary, `Ar`)
- Sélection des médicaments capables de guérir le patient
- Page 2: médicaments respectant le budget
- Page 3: alternatives les moins chères (même si > budget)

Algorithme:
- Pour chaque médicament (n) et pour chaque symptôme (m), vérifier si
  symptome_final = symptome_patient + effet <= 0
- Complexité: O(n * m)

Compilation et exécution (Windows):
1. Ouvrez un terminal PowerShell dans le dossier du projet.
2. Compilez :
   ```powershell
   javac -d out src\*.java
   ```
3. Lancez :
   ```powershell
   java -cp out App
   ```

Base de données MySQL (optionnel):
- Le projet fournit une implémentation `DatabaseUtil.getSampleMedicines()` en
  mémoire pour la démo. Pour utiliser MySQL :
  - Ajouter le driver JDBC (MySQL Connector/J) au classpath.
  - Implémenter dans `DatabaseUtil` des méthodes qui ouvrent une connexion
    JDBC, lisent les tables `Symptome`, `Medicament`, `Effet` et construisent
    les objets `Medicine` et `Effect`.

Architecture (MVC):
- `Symptom`, `Medicine`, `Effect` : modèles
- `DatabaseUtil` : utilitaires / source de données
- `Controller` : logique de sélection (O(n*m))
- `MainView` : interface Swing (3 pages, CardLayout)

Remarques pédagogiques:
- Aucune bibliothèque/framework externe n'est utilisé pour la sélection;
  l'algorithme parcourt simplement les entrées.
- Vous pouvez étendre l'interface pour charger/enregistrer depuis MySQL.
## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).

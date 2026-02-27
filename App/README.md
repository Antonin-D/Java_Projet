# Java_Projet


## Partie Inferface Graphique 
* **`MainLayout.fxml`** : Le squelette principal de l'application (basé sur un `BorderPane`). Il intègre le menu de navigation latéral statique, permettant de garder une base fixe lors du changement d'écrans.
* **`PersonList.fxml`** : L'écran d'affichage regroupant tous les contacts. Il contient un grand tableau dynamique (`TableView`) dont les colonnes s'adaptent automatiquement à la fenêtre pour afficher des informations complètes (Nom, Prénom, Surnom, Téléphone, Adresse, Email et la date de naissance).
* **`PersonAdd.fxml`** : Le formulaire de création de contact, structuré avec des champs de texte classiques et un calendrier interactif (`DatePicker`) pour la date de naissance.
* **`style.css`** : La feuille de style qui centralise toute la charte graphique. 

### ⚙️ La Logique et les Contrôleurs 

* **`MainLayoutController.java`** : Le contrôleur de routage. Il permet une navigation fluide sans jamais recharger l'ensemble de la fenêtre.
* **`PersonAdminController.java`** : Le contrôleur responsable de la liste des contacts. Il fait le pont entre la base de données et l'interface graphique. Grâce à des `PropertyValueFactory`, il mappe automatiquement les attributs de la classe `Person` aux différentes colonnes du tableau, puis charge les données via la méthode `personDao.listPersons()`.
* **`PersonAddController.java`** : Le contrôleur du formulaire. Lors de la soumission, il récupère les saisies de l'utilisateur, vérifie que les champs obligatoires (nom et prénom) ne sont pas vides pour éviter les erreurs, instancie un nouvel objet `Person`, l'envoie en base de données via `personDao.addPerson()`, puis réinitialise automatiquement le formulaire.
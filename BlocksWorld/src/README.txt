-----------------------------------------------------------------------
    README - CC3 - Aide à la décision en intelligence artificielle
-----------------------------------------------------------------------

* Le travail consiste à la réalisation du projet "Fil Rouge" qui est
l'implementation  monde des blocs (BlocksWorld), un exemple courament 
utilisé en Intelligence Artificielle. 

* Ce-dernier est réalisé en Java par le binome : 
 ________________________________________________
| Nom         | Prénom      | Numéro d'étudiant  |
|-------------|-------------|--------------------|
| DJEHA       | Wassim      | 22208244           |
| KACED       | Louheb      | 22111744           |
|_____________|_____________|____________________|

* Le projet comporte plusieurs parties essentielles qui sont : 

-----------------------------------------------------------------------
			     Modélisation
-----------------------------------------------------------------------
1. Modélisation des configurations du monde des blocs et définition des 
contraintes.
* classe executable : DemoModelling

-----------------------------------------------------------------------
			     Planification
-----------------------------------------------------------------------
2.
- Implémentation des actions de déplacement des blocs et implementation
de differents algorithmes de planification.
- Utilisation d'une heuristic qui consiste à calculer le nombre de blocs 
mal placés dans chaque etat.
- Utilisation d'une heuristic qui estime la distance qu'un bloc doit 
parcourir pour atteindre sa position cible
* classe executable : DemoPlanning

-----------------------------------------------------------------------
		Problème de satisfaction de contraintes
-----------------------------------------------------------------------
3. Utilisation de solveurs pour vérifier la satisfaction des contraintes.
* classes executable : 
  - Configuration Reguliere : DemoReguliere
  - Configuration Croissante : DemoCroissante
  - Configuration Reguliere & Croissante : DemoCroissanteReguliere

-----------------------------------------------------------------------
		      Extraction de connaissances
-----------------------------------------------------------------------
4. Création d'une base de données et extraction de motifs et règles 
d'association utilisant la librairie fournie bwgenerator.
* classe executable : DemoDataMining

-----------------------------------------------------------------------
		              Librairies
-----------------------------------------------------------------------
5. 
 - Utilisation de la librairie blocksworld pour représenter et visualiser
 les configurations.
 - Utilisation de la librairie bwgenerator pour l'extraction de connaissances.

-----------------------------------------------------------------------
		Commandes de compilation et d'execution
-----------------------------------------------------------------------
* On ouvre un terminal dans le repertoire source /src , on tape :
  1. Pour Compiler:

 	javac -d build -cp .:lib/blocksworld.jar:lib/bwgenerator.jar 
	modelling/*.java planning/*.java cp/*.java datamining/*.java
	blocksworld/*.java

  2. Pour executer:
	java -cp build:lib/blocksworld.jar:lib/bwgenerator.jar 	blocksworld.NOM_CLASSE_EXECUTABLE

(!) On remplace "NOM_CLASSE_EXECUTABLE" par le nom de la classe executable 
de la partie qu'on veut (!)




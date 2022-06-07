# Touch - Pause - Engage

La dernière journée de Top 14 de l'édition 2021-2022 était particulièrement
serrée, avec dans le peloton de tête pas mal de clubs dans un mouchoir de poche.

Via ce petit projet, j'ai tenté de représenter visuellement l'évolution du 
classement, minute par minute.

Pour ce faire j'ai :

- réfléchi à un modèle de données permettant de retracer le cours d'un match,
  l'évolution du score et l'évolution des points attribués à chaque équipe (en
  fonction du score, des bonus offensifs et défensifs éventuels)
- saisi [l'intégralité des actions de marque des 7 rencontres de la 26ᵉ journée 😓](https://github.com/legzo/touch-pause-engage/blob/main/src/main/kotlin/matches-data.kt),
  à l'aide des données trouvées sur [Rugbyrama](https://www.rugbyrama.fr/rugby/top-14/calendar-result.shtml) 
- transformé tout ça en un json que j'utilise ensuite côté front pour grapher,
  avec [Observable Plot](https://github.com/observablehq/plot) l'évolution du
  classement, action par action.

Et ça donne ça !

![screenshot](./screenshot.png)

Dans un premier temps, j'ai généré le JSON en one-shot (depuis un TU 😱) et
injecté le JSON dans le HTML, dans l'idéal, il faudrait probablement APIser ça.
Mais ce sera pour une prochaine fois !

❤

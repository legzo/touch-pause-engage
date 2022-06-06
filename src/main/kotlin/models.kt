import Marque.*

enum class Equipe {
    Bordeaux, Perpignan
}

enum class Marque(val points: Int) {
    EssaiTransforme(7), Essai(5), Penalite(3),
}

data class ActionDeMarque(
    val minute: Int,
    val equipe: Equipe,
    val action: Marque,
)

data class Score(
    val minute: Int,
    val equipeA: Equipe,
    val equipeB: Equipe,
    val scoreEquipeA: Int,
    val scoreEquipeB: Int,
    val marques: List<ActionDeMarque>,
)

data class Points(
    val minute: Int,
    val equipeA: Equipe,
    val equipeB: Equipe,
    val pointsEquipeA: Int,
    val pointsEquipeB: Int,
)

data class Match(
    val equipeA: Equipe,
    val equipeB: Equipe,
    val marques: List<ActionDeMarque>,
)

fun calculeScore(match: Match) = calculeEvolutionScore(match).last()

fun calculeEvolutionScore(match: Match): List<Score> = match.marques.scan(
    Score(
        minute = 0,
        equipeA = match.equipeA,
        equipeB = match.equipeB,
        scoreEquipeA = 0,
        scoreEquipeB = 0,
        marques = listOf()
    )
) { acc, it ->
    acc.copy(
        minute = it.minute,
        scoreEquipeA = if (it.equipe == match.equipeA) acc.scoreEquipeA + it.action.points else acc.scoreEquipeA,
        scoreEquipeB = if (it.equipe == match.equipeB) acc.scoreEquipeB + it.action.points else acc.scoreEquipeB,
        marques = acc.marques + it
    )
}


fun Score.toPoints(): Points {
    val nombreEssaisEquipeA = marques.essaisEquipe(equipeA)
    val nombreEssaisEquipeB = marques.essaisEquipe(equipeB)

    return Points(
        minute = minute,
        equipeA = equipeA,
        equipeB = equipeB,
        pointsEquipeA = calculePoints(scoreEquipeA, nombreEssaisEquipeA, scoreEquipeB, nombreEssaisEquipeB),
        pointsEquipeB = calculePoints(scoreEquipeB, nombreEssaisEquipeB, scoreEquipeA, nombreEssaisEquipeA),
    )
}

private fun calculePoints(
    scoreEquipe: Int,
    nombreEssaisEquipe: Int,
    scoreAutreEquipe: Int,
    nombreEssaisAutreEquipe: Int,
) = when {
    scoreEquipe > scoreAutreEquipe -> 4
    scoreAutreEquipe > scoreEquipe -> 0
    else -> 2
} +
    if (nombreEssaisEquipe >= nombreEssaisAutreEquipe + 3) 1 else 0 +
    if (scoreEquipe in ((scoreAutreEquipe - 5) until scoreAutreEquipe)) 1 else 0

private fun List<ActionDeMarque>.essaisEquipe(equipe: Equipe) =
    this.filter { it.equipe == equipe }.count { it.action == Essai || it.action == EssaiTransforme }

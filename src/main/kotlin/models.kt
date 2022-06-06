import Marque.*

enum class Equipe {
    Bordeaux, Perpignan
}

enum class Marque(val points: Int) {
    EssaiTransformé(7), Essai(5), Penalite(3),
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

fun calculeScore(match: Match) =
    calculeEvolutionScore(match).last()

fun calculeEvolutionScore(match: Match): List<Score> =
    match.marques
        .scan(
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
    val pointsRelatifsAuScore: Pair<Int, Int> =
        when {
            scoreEquipeA > scoreEquipeB -> 4 to 0
            scoreEquipeB > scoreEquipeA -> 0 to 4
            else -> 2 to 2
        }

    val nombreEssaisEquipeA = marques.essaisEquipe(equipeA)
    val nombreEssaisEquipeB = marques.essaisEquipe(equipeB)

    val bonusOffensifA = if (nombreEssaisEquipeA >= nombreEssaisEquipeB + 3) 1 else 0
    val bonusOffensifB = if (nombreEssaisEquipeB >= nombreEssaisEquipeA + 3) 1 else 0

    val bonusDefensifA = if (scoreEquipeA in ((scoreEquipeB - 5) until scoreEquipeB)) 1 else 0
    val bonusDefensifB = if (scoreEquipeB in ((scoreEquipeA - 5) until scoreEquipeA)) 1 else 0

    return Points(
        minute = minute,
        equipeA = equipeA,
        equipeB = equipeB,
        pointsEquipeA = pointsRelatifsAuScore.first + bonusOffensifA + bonusDefensifA,
        pointsEquipeB = pointsRelatifsAuScore.second + bonusOffensifB + bonusDefensifB,
    )
}

private fun List<ActionDeMarque>.essaisEquipe(equipe: Equipe) =
    this
        .filter { it.equipe == equipe }
        .count { it.action == Essai || it.action == EssaiTransformé }

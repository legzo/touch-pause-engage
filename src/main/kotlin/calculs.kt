import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun calculeScore(match: Match) = calculeEvolutionScore(match).last()

fun calculeEvolutionScore(match: Match): List<Score> =
    match.marques.scan(
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
): Int {
    val pointsRelatifsAuScore = when {
        scoreEquipe > scoreAutreEquipe -> 4
        scoreAutreEquipe > scoreEquipe -> 0
        else -> 2
    }

    val bonusOffensif = if (nombreEssaisEquipe >= nombreEssaisAutreEquipe + 3) 1 else 0
    val bonusDefensif = if (scoreEquipe in ((scoreAutreEquipe - 5) until scoreAutreEquipe)) 1 else 0

    return pointsRelatifsAuScore + bonusOffensif + bonusDefensif
}

fun calculeEvolutionClassement(
    classementInitial: Classement,
    matches: Set<Match>
): List<Classement> =
    matches
        .flatMap { calculeEvolutionScore(it).drop(1) }
        .sortedBy { it.minute }
        .scan(classementInitial) { classement, score ->
            val points = score.toPoints()
            classement.copy(
                minute = score.minute,
                equipes = classement.equipes.mapValues { (equipe, pointsAuClassement) ->
                    when {
                        points.equipeA == equipe ->
                            classementInitial[equipe].update(
                                points.pointsEquipeA,
                                score.scoreEquipeA - score.scoreEquipeB
                            )
                        points.equipeB == equipe ->
                            classementInitial[equipe].update(
                                points.pointsEquipeB,
                                score.scoreEquipeB - score.scoreEquipeA
                            )
                        else -> pointsAuClassement
                    }
                }
            )
        }


fun calculeStatsEvolutionClassement(
    classementInitial: Classement,
    matches: Set<Match>
): String {
    val stats = calculeEvolutionClassement(classementInitial, matches).toStats()
    return Json.encodeToString(stats)
}

fun List<Classement>.toStats(): List<Position> {

    val classementAvant: List<Equipe> = first().sorted().map { it.key }
    val classementApres: List<Equipe> = last().sorted().map { it.key }

    val nomsAvecInfo: Map<Equipe, String> = libellesEquipes(classementAvant, classementApres)

    return flatMap {
        it.sorted().mapIndexed { index, position ->
            Position(
                minute = it.minute,
                equipe = nomsAvecInfo[position.key] ?: position.key.toString(),
                classement = index + 1
            )
        }
    }
}

private fun libellesEquipes(
    classementAvant: List<Equipe>,
    classementApres: List<Equipe>
): Map<Equipe, String> {
    val nomsAvecInfo: Map<Equipe, String> = Equipe.values().associateWith {
        val positionAvant = classementAvant.indexOf(it) + 1
        val positionApres = classementApres.indexOf(it) + 1
        if (positionAvant != positionApres) {
            "$it ($positionAvant → $positionApres)"
        } else "$it"
    }
    return nomsAvecInfo
}

import Marque.Essai
import Marque.EssaiTransforme

enum class Equipe {
    Bordeaux, Perpignan, Toulouse, Biarritz
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

data class Classement(
    val equipes: Map<Equipe, PositionClassement>
) {
    operator fun get(equipe: Equipe): PositionClassement = equipes[equipe] ?: throw IllegalArgumentException("Err !")

    fun sorted(): List<Map.Entry<Equipe, PositionClassement>> =
        equipes.entries
            .sortedWith(
                compareBy(
                    { (_, position) -> position.points },
                    { (_, position) -> position.differenceDePoints })
            ).reversed()
}

data class PositionClassement(
    val points: Int,
    val differenceDePoints: Int
) {
    fun update(
        pointsAdditionnels: Int,
        scoreAdditionnel: Int
    ) =
        this.copy(
            points = points + pointsAdditionnels,
            differenceDePoints = differenceDePoints + scoreAdditionnel
        )

    override fun toString(): String {
        return "$points ($differenceDePoints)"
    }
}


internal fun List<ActionDeMarque>.essaisEquipe(equipe: Equipe) =
    this
        .filter { it.equipe == equipe }
        .count { it.action == Essai || it.action == EssaiTransforme }

import Marque.Essai
import Marque.EssaiTransforme
import Marque.Penalite
import kotlinx.serialization.Serializable

enum class Equipe {
    Biarritz, Bordeaux, Brive, Castres, Clermont, LaRochelle, Lyon, Montpellier, Pau, Perpignan, Racing92, StadeFrancais, Toulon, Toulouse
}

enum class Marque(val points: Int) {
    EssaiTransforme(7), Essai(5), Penalite(3),
}

data class ActionDeMarque(
    val minute: Int,
    val equipe: Equipe,
    val action: Marque,
) {
    companion object {
        fun essaiTransforme(minute: Int, equipe: Equipe) = ActionDeMarque(minute, equipe, EssaiTransforme)
        fun essai(minute: Int, equipe: Equipe) = ActionDeMarque(minute, equipe, Essai)
        fun penalite(minute: Int, equipe: Equipe) = ActionDeMarque(minute, equipe, Penalite)
    }
}

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
    val minute: Int = 0,
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

@Serializable
data class Position(
    val minute: Int,
    val equipe: Equipe,
    val classement: Int,
)

internal fun List<ActionDeMarque>.essaisEquipe(equipe: Equipe) =
    this
        .filter { it.equipe == equipe }
        .count { it.action == Essai || it.action == EssaiTransforme }

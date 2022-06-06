import Equipe.*
import Marque.*
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class MatchShould {

    private val matchPerpignanBordeaux = Match(
        equipeA = Perpignan,
        equipeB = Bordeaux,
        marques = listOf(
/**/
            ActionDeMarque(8, Bordeaux, EssaiTransformé),
            ActionDeMarque(10, Perpignan, EssaiTransformé),
            ActionDeMarque(28, Perpignan, Penalite),
            ActionDeMarque(32, Bordeaux, Essai),
            ActionDeMarque(34, Perpignan, EssaiTransformé),
            ActionDeMarque(53, Perpignan, Essai),
            ActionDeMarque(61, Bordeaux, Penalite),
        ),
    )

    @Test
    fun `calcule le score final d'un match sans points`() {
        val match = Match(
            equipeA = Bordeaux,
            equipeB = Perpignan,
            marques = listOf(),
        )

        with(calculeScore(match)) {
            scoreEquipeA shouldBe 0
            scoreEquipeB shouldBe 0
        }
    }

    @Test
    fun `calcule le score final d'un match`() {
        with(calculeScore(matchPerpignanBordeaux)) {
            scoreEquipeA shouldBe 22
            scoreEquipeB shouldBe 15
        }
    }

    @Test
    fun `calcule l'evolution du score d'un match`() {
        calculeEvolutionScore(matchPerpignanBordeaux)
            .map { it.scoreEquipeA to it.scoreEquipeB } shouldBe
                listOf(
                    0 to 0,
                    0 to 7,
                    7 to 7,
                    10 to 7,
                    10 to 12,
                    17 to 12,
                    22 to 12,
                    22 to 15,
                )
    }

    @Test
    fun `calcule l'evolution des points d'un match`() {
        calculeEvolutionScore(matchPerpignanBordeaux)
            .map { it.toPoints() }
            .map { it.pointsEquipeA to it.pointsEquipeB } shouldBe
                listOf(
                    2 to 2,
                    0 to 4,
                    2 to 2,
                    4 to 1,
                    1 to 4,
                    4 to 1,
                    4 to 0,
                    4 to 0,
                )
    }
}
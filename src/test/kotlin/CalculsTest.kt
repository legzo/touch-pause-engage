import Equipe.*
import Marque.*
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class CalculsTest {

    private val matchPerpignanBordeaux = Match(
        equipeA = Perpignan,
        equipeB = Bordeaux,
        marques = listOf(
            ActionDeMarque(8, Bordeaux, EssaiTransforme),
            ActionDeMarque(10, Perpignan, EssaiTransforme),
            ActionDeMarque(28, Perpignan, Penalite),
            ActionDeMarque(32, Bordeaux, Essai),
            ActionDeMarque(34, Perpignan, EssaiTransforme),
            ActionDeMarque(53, Perpignan, Essai),
            ActionDeMarque(61, Bordeaux, Penalite),
        ),
    )

    private val matchToulouseBiarritz = Match(
        equipeA = Toulouse,
        equipeB = Biarritz,
        marques = listOf(
            ActionDeMarque(8, Toulouse, EssaiTransforme),
            ActionDeMarque(12, Toulouse, EssaiTransforme),
            ActionDeMarque(30, Biarritz, EssaiTransforme),
            ActionDeMarque(36, Toulouse, EssaiTransforme),
            ActionDeMarque(41, Toulouse, Essai),
            ActionDeMarque(42, Toulouse, EssaiTransforme),
            ActionDeMarque(45, Toulouse, EssaiTransforme),
            ActionDeMarque(52, Toulouse, EssaiTransforme),
            ActionDeMarque(57, Toulouse, Essai),
            ActionDeMarque(71, Toulouse, EssaiTransforme),
            ActionDeMarque(73, Toulouse, EssaiTransforme),
            ActionDeMarque(75, Toulouse, EssaiTransforme),
            ActionDeMarque(79, Toulouse, EssaiTransforme),
        )
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

        with(calculeScore(matchToulouseBiarritz)) {
            scoreEquipeA shouldBe 80
            scoreEquipeB shouldBe 7
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

        with(calculeEvolutionScore(matchToulouseBiarritz)
            .map { it.toPoints() }
            .map { it.pointsEquipeA to it.pointsEquipeB }) {
            first() shouldBe (2 to 2)
            last() shouldBe (5 to 0)
        }
    }


    @Test
    fun `calcule l'evolution du classement`() {
        val classementInitial = Classement(
            equipes = mapOf(
                Bordeaux to PositionClassement(72, 133),
                Perpignan to PositionClassement(39, -180),
                Toulouse to PositionClassement(66, 133),
                Biarritz to PositionClassement(24, -363)
            )
        )
        val evolClassement = calculeEvolutionClassement(
            classementInitial, setOf(matchPerpignanBordeaux, matchToulouseBiarritz)
        )

        with(evolClassement) {
            first() shouldBe classementInitial
            last() shouldBe Classement(
                equipes = mapOf(
                    Bordeaux to PositionClassement(72, 126),
                    Perpignan to PositionClassement(43, -173),
                    Toulouse to PositionClassement(71, 206),
                    Biarritz to PositionClassement(24, -436)
                )
            )

            last().sorted().map { it.key } shouldBe listOf(
                Bordeaux,
                Toulouse,
                Perpignan,
                Biarritz
            )
        }


    }
}
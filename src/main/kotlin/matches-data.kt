import ActionDeMarque.Companion.essai
import ActionDeMarque.Companion.essaiTransforme
import ActionDeMarque.Companion.penalite
import Equipe.Biarritz
import Equipe.Bordeaux
import Equipe.Brive
import Equipe.Castres
import Equipe.Clermont
import Equipe.LaRochelle
import Equipe.Lyon
import Equipe.Montpellier
import Equipe.Pau
import Equipe.Perpignan
import Equipe.Racing92
import Equipe.StadeFrancais
import Equipe.Toulon
import Equipe.Toulouse

val matchClermontMontpellier = Match(
    equipeA = Clermont, equipeB = Montpellier, marques = listOf(
        essaiTransforme(7, Clermont),
        penalite(11, Montpellier),
        penalite(42, Clermont),
        essai(51, Montpellier),
        essaiTransforme(53, Clermont),
        essaiTransforme(67, Montpellier),
        penalite(74, Clermont),
    )
)

val matchLyonLaRochelle = Match(
    equipeA = Lyon, equipeB = LaRochelle, marques = listOf(
        essaiTransforme(4, Lyon),
        essai(12, LaRochelle),
        essai(20, Lyon),
        essaiTransforme(40, Lyon),
        essaiTransforme(45, LaRochelle),
        essai(51, LaRochelle),
        essaiTransforme(55, LaRochelle),
        essaiTransforme(69, Lyon),
        essai(74, LaRochelle),

        )
)

val matchRacing92Toulon = Match(
    equipeA = Racing92, equipeB = Toulon, marques = listOf(
        penalite(5, Toulon),
        penalite(9, Toulon),
        essai(13, Racing92),
        essaiTransforme(24, Toulon),
        penalite(27, Racing92),
        penalite(46, Racing92),
        penalite(50, Toulon),
        penalite(53, Racing92),
        essaiTransforme(62, Racing92),
    )
)

val matchPauCastres = Match(
    equipeA = Pau,
    equipeB = Castres,
    marques = listOf(
        essaiTransforme(1, Castres),
        essaiTransforme(4, Pau),
        penalite(10, Castres),
        penalite(32, Pau),
        essaiTransforme(39, Castres),
        penalite(45, Pau),
        penalite(62, Pau),
        penalite(62, Castres),
        penalite(65, Castres),
        penalite(71, Castres),
    )
)

val matchStadeFrancaisBrive = Match(
    equipeA = StadeFrancais,
    equipeB = Brive,
    marques = listOf(
        penalite(2, Brive),
        essaiTransforme(6, StadeFrancais),
        penalite(11, Brive),
        essaiTransforme(25, Brive),
        penalite(29, StadeFrancais),
        essaiTransforme(37, Brive),
        penalite(44, Brive),
        penalite(51, Brive),
        essaiTransforme(62, Brive),
        essaiTransforme(77, StadeFrancais),
    )
)

val matchPerpignanBordeaux = Match(
    equipeA = Perpignan,
    equipeB = Bordeaux,
    marques = listOf(
        essaiTransforme(8, Bordeaux),
        essaiTransforme(10, Perpignan),
        penalite(28, Perpignan),
        essai(32, Bordeaux),
        essaiTransforme(34, Perpignan),
        essai(53, Perpignan),
        penalite(61, Bordeaux),
    ),
)

val matchToulouseBiarritz = Match(
    equipeA = Toulouse, equipeB = Biarritz, marques = listOf(
        essaiTransforme(8, Toulouse),
        essaiTransforme(12, Toulouse),
        essaiTransforme(30, Biarritz),
        essaiTransforme(36, Toulouse),
        essai(41, Toulouse),
        essaiTransforme(42, Toulouse),
        essaiTransforme(45, Toulouse),
        essaiTransforme(52, Toulouse),
        essai(57, Toulouse),
        essaiTransforme(71, Toulouse),
        essaiTransforme(73, Toulouse),
        essaiTransforme(75, Toulouse),
        essaiTransforme(79, Toulouse),
    )
)

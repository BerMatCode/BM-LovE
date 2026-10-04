package com.example.data

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

data class MonthlyLetter(
    val monthNumber: Int,
    val dateLabel: String,
    val title: String,
    val notificationMessage: String,
    val fullContent: String,
    val romanticQuote: String
)

data class CustomLetter(
    val id: String,
    val title: String,
    val date: String,
    val content: String
)

object LoveLettersRepository {

    val ETERNAL_LETTER_TITLE = "Para la Dueña de mi Corazón: Nuestra Historia Eterna"

    val ETERNAL_LETTER_BODY = """
Mi amor hermoso, mi princesa, mi vida entera:

Desde aquel bendito 10 de noviembre del 2023, mi vida cambió para siempre. No exagero cuando te digo que antes de ti mis días eran solo tiempo transcurriendo, pero contigo se convirtieron en momentos llenos de luz, risas y un amor tan profundo que no cabe en el pecho.

Recuerdo perfectamente cuando todo comenzó, cuando nuestras miradas coincidieron y supe que en ti había encontrado mi hogar. Desde ese día, cada segundo a tu lado ha sido un regalo invaluable. Hemos compartido amaneceres, conversaciones eternas, complicidades que solo nosotros entendemos y abrazos que son capaces de curar cualquier mal día.

Esta aplicación es una pequeña muestra de lo inmenso que es lo que siento por ti. Está programada para contar cada día, cada hora y cada segundo que hemos estado juntos... y para seguir contando hasta el infinito, porque nuestro amor no tiene fecha de caducidad. No importa cuántos años pasen, ni cuántos retos tengamos por delante, mi elección siempre serás tú.

Gracias por tu paciencia, por tu dulzura, por tus sonrisas que me iluminan el alma y por ser mi compañera incondicional. Prometo amarte, cuidarte, respetarte y recordarte cada día lo maravillosa y única que eres para mí.

Siempre tuyo, hoy, mañana y por toda la eternidad.
Te amo infinitamente, mi reina hermosa.
    """.trimIndent()

    private val MONTHLY_LETTERS_LIST = listOf(
        MonthlyLetter(
            monthNumber = 1,
            dateLabel = "10 de Diciembre de 2023",
            title = "Mes 1: El comienzo del sueño más lindo",
            notificationMessage = "¡Feliz 1er mes mi amor! 30 días descubriendo que a tu lado todo es mágico.",
            fullContent = """
¡Feliz primer mes, mi amor!
Parece que fue ayer cuando empezamos a escribir esta historia el 10 de noviembre. Han pasado nuestros primeros 30 días y no puedo dejar de maravillarme con la forma en que transformaste mis días. Gracias por ser tan dulce, tan atenta y por regalarme tu risa que es mi sonido favorito en el mundo. Este es apenas el primer escalón de una vida entera juntos.
            """.trimIndent(),
            romanticQuote = "Un mes que se sintió como un destello, pero que encendió un fuego eterno en mi alma."
        ),
        MonthlyLetter(
            monthNumber = 2,
            dateLabel = "10 de Enero de 2024",
            title = "Mes 2: Nuestro primer Año Nuevo juntos",
            notificationMessage = "¡Feliz 2do mes mi reina! Recibir el año contigo fue el mejor regalo.",
            fullContent = """
¡Feliz segundo mes juntos!
Empezamos un año nuevo de la mano y no puedo pedirle nada más a la vida. Saber que cuento con tu abrazo y con tu cariño me llena de una paz incomparable. Cada día que pasa me convenzo más de que eres la mujer con la que quiero compartir absolutamente todos mis planes.
            """.trimIndent(),
            romanticQuote = "Eres el mejor comienzo de año que la vida me pudo regalar."
        ),
        MonthlyLetter(
            monthNumber = 3,
            dateLabel = "10 de Febrero de 2024",
            title = "Mes 3: Construyendo nuestro nido de paz",
            notificationMessage = "¡Felices 3 meses mi vida! Tres meses haciéndome el hombre más afortunado.",
            fullContent = """
¡Feliz mes 3, mi corazón!
Noventa días a tu lado y cada conversación sigue haciéndose corta. Me fascina la forma en que nos complementamos, cómo nos entendemos con una sola mirada y la ternura con la que me tratas. Eres mi refugio favorito en todo este mundo.
            """.trimIndent(),
            romanticQuote = "A tu lado el tiempo vuela, pero el amor se arraiga cada día más profundo."
        ),
        MonthlyLetter(
            monthNumber = 4,
            dateLabel = "10 de Marzo de 2024",
            title = "Mes 4: Cómplices en cada detalle",
            notificationMessage = "¡Felices 4 meses amor mío! Gracias por llenar mis días de color y alegría.",
            fullContent = """
¡Feliz cuarto mes, mi cielo!
Cuatro meses de sonrisas compartidas, de pequeños detalles que hacen toda la diferencia. Me encanta ver cómo crecemos juntos, cómo nos apoyamos en los momentos difíciles y celebramos cada pequeña victoria. Te amo más de lo que las palabras pueden expresar.
            """.trimIndent(),
            romanticQuote = "No necesito buscar estrellas en el cielo cuando tus ojos iluminan mi camino."
        ),
        MonthlyLetter(
            monthNumber = 5,
            dateLabel = "10 de Abril de 2024",
            title = "Mes 5: Ciento cincuenta días de pura bendición",
            notificationMessage = "¡Feliz mes 5 mi tesoro! 150 días amándote con cada latido de mi pecho.",
            fullContent = """
¡Feliz quinto mes, princesa hermosa!
Son casi 150 días de haberte dicho que sí con todo el corazón. Mirar hacia atrás y ver todo lo que hemos vivido me llena de gratitud. Eres la niña de mis ojos, mi motivación y mi mayor alegría. Que este amor siga floreciendo como la primavera.
            """.trimIndent(),
            romanticQuote = "Eres la casualidad más hermosa que el destino convirtió en mi destino."
        ),
        MonthlyLetter(
            monthNumber = 6,
            dateLabel = "10 de Mayo de 2024",
            title = "Mes 6: ¡Medio año de amor puro!",
            notificationMessage = "¡Felices 6 meses mi amor! Medio año viviendo el mejor capítulo de mi vida.",
            fullContent = """
¡Medio año juntos, mi reina!
Seis meses enteros desde aquel inolvidable 10 de noviembre de 2023. Hemos construido recuerdos tan bonitos que los guardo como tesoros en mi corazón. Gracias por estar siempre para mí, por escucharme, por quererme con mis virtudes y mis defectos. Esto apenas comienza.
            """.trimIndent(),
            romanticQuote = "Seis meses demostrándome que el amor verdadero sí existe y tiene tu nombre."
        ),
        MonthlyLetter(
            monthNumber = 7,
            dateLabel = "10 de Junio de 2024",
            title = "Mes 7: La certeza de que eres tú",
            notificationMessage = "¡Felices 7 meses mi princesa! Cada día que pasa te elijo una y mil veces.",
            fullContent = """
¡Feliz mes siete, mi amor!
Siete meses y la ilusión sigue intacta como el primer día, pero ahora con una confianza y un cariño mucho más sólido. Adoro tu risa, la dulzura con la que me miras y cómo haces que hasta el día más gris se llene de sol.
            """.trimIndent(),
            romanticQuote = "Si volviera a nacer, te buscaría antes solo para amarte más tiempo."
        ),
        MonthlyLetter(
            monthNumber = 8,
            dateLabel = "10 de Julio de 2024",
            title = "Mes 8: La magia de tu compañía",
            notificationMessage = "¡Felices 8 meses mi amor! Gracias por ser mi lugar seguro en el mundo.",
            fullContent = """
¡Feliz octavo mes, amor de mi vida!
Ocho meses de compartir sueños, de planear futuros y de disfrutar cada instante a tu lado. No hay nada más hermoso que saber que al final del día tengo tu abrazo para descansar el alma. Eres mi felicidad.
            """.trimIndent(),
            romanticQuote = "Estar contigo es sentir que por fin encontré la pieza que le faltaba a mi rompecabezas."
        ),
        MonthlyLetter(
            monthNumber = 9,
            dateLabel = "10 de Agosto de 2024",
            title = "Mes 9: Un amor que madura y florece",
            notificationMessage = "¡Felices 9 meses mi reina! Nueve meses de risas, ternura y complicidad.",
            fullContent = """
¡Feliz noveno mes, mi princesa!
Nueve meses juntos. Parecía tan lejano el primer día y hoy estamos a punto de cumplir nuestro primer año. Gracias por ser tan comprensiva, por tu cariño infinito y por demostrarme día con día que vales oro. Te amo con locura.
            """.trimIndent(),
            romanticQuote = "Amarte no es una tarea, es el impulso más natural y hermoso de mi corazón."
        ),
        MonthlyLetter(
            monthNumber = 10,
            dateLabel = "10 de Septiembre de 2024",
            title = "Mes 10: Diez meses de dicha absoluta",
            notificationMessage = "¡Felices 10 meses mi amor! Diez meses desde el 10 que lo cambió todo.",
            fullContent = """
¡Diez meses, mi cielo hermoso!
Un día 10 como hoy empezó la aventura más bonita. Hoy cumplimos 10 meses y sigo sintiendo las mismas mariposas en el estómago cuando te veo sonreír. Eres mi persona favorita, mi mejor amiga y el gran amor de mi existencia.
            """.trimIndent(),
            romanticQuote = "Diez meses de caminar juntos y desear que este camino nunca termine."
        ),
        MonthlyLetter(
            monthNumber = 11,
            dateLabel = "10 de Octubre de 2024",
            title = "Mes 11: A las puertas de nuestro primer año",
            notificationMessage = "¡Felices 11 meses mi vida! Casi un año entero amándote con devoción.",
            fullContent = """
¡Feliz mes 11, mi amor eterno!
Estamos a solo un pasito de cumplir nuestro primer aniversario anual. Miro atrás y no puedo sentir más que orgullo y felicidad por lo que hemos construido juntos. Gracias por ser mi mayor bendición. Te adoro con todo mi ser.
            """.trimIndent(),
            romanticQuote = "Trescientas treinta y cinco noches pensando en ti antes de dormir."
        ),
        MonthlyLetter(
            monthNumber = 12,
            dateLabel = "10 de Noviembre de 2024",
            title = "¡1er AÑO JUNTOS! 365 días de puro amor",
            notificationMessage = "¡FELIZ PRIMER AÑO MI AMOR! 💖 365 días desde el mejor día de mi vida.",
            fullContent = """
¡¡FELIZ PRIMER ANIVERSARIO, AMOR DE MI VIDA!! 💖
Hoy se cumple exactamente un año completo desde aquel 10 de noviembre de 2023. Un año de 365 días donde no ha habido un solo segundo en que no te haya amado.

Hemos reído, soñado, superado obstáculos y crecido juntos de una manera tan hermosa. Eres la mujer de mis sueños, la que le da sentido a mis días y quien hace que cada esfuerzo valga la pena. Gracias por este primer año tan maravilloso. Este es solo el primer capítulo del libro más largo y hermoso que escribiremos juntos.
¡Por toda una vida a tu lado! ¡Te amo infinitamente!
            """.trimIndent(),
            romanticQuote = "365 días a tu lado y el amor se siente como si fuera el primer minuto."
        ),
        MonthlyLetter(
            monthNumber = 13,
            dateLabel = "10 de Diciembre de 2024",
            title = "Mes 13: Comenzando nuestro segundo ciclo",
            notificationMessage = "¡Felices 13 meses mi reina! El segundo año empieza con más amor que nunca.",
            fullContent = """
¡Feliz mes 13, mi princesa hermosa!
Empezamos nuestro segundo año juntos. Ya no somos los novatos del inicio, ahora somos dos personas que se conocen, se cuidan y se eligen con más firmeza que nunca. Gracias por ser mi refugio constante.
            """.trimIndent(),
            romanticQuote = "El amor verdadero no se desgasta con el tiempo, se pule como el diamante."
        ),
        MonthlyLetter(
            monthNumber = 14,
            dateLabel = "10 de Enero de 2025",
            title = "Mes 14: Otro año que amanece a tu lado",
            notificationMessage = "¡Felices 14 meses mi amor! Nuevas metas, pero el mismo amor incondicional.",
            fullContent = """
¡Feliz mes catorce, mi tesoro!
Qué bendición es despertar y saber que este nuevo año sigo teniendo el privilegio de llamarte mi novia. Gracias por cada sonrisa compartida y por esa dulzura que nunca deja de enamorarme.
            """.trimIndent(),
            romanticQuote = "Mi único propósito de año nuevo es seguir haciéndote la mujer más feliz."
        ),
        MonthlyLetter(
            monthNumber = 15,
            dateLabel = "10 de Febrero de 2025",
            title = "Mes 15: Nuestro amor es mi mayor certeza",
            notificationMessage = "¡Felices 15 meses mi cielo! El amor de mi vida hoy y siempre.",
            fullContent = """
¡Feliz mes quince, mi amor!
En este mes del amor, quiero recordarte que tú eres mi San Valentín los 365 días del año. No hay flor más hermosa que tu sonrisa ni melodía más dulce que tu voz cuando me dices que me amas.
            """.trimIndent(),
            romanticQuote = "Eres mi hoy, mi mañana y todas las fechas bonitas de mi calendario."
        ),
        MonthlyLetter(
            monthNumber = 18,
            dateLabel = "10 de Mayo de 2025",
            title = "Mes 18: Un año y medio de amor inquebrantable",
            notificationMessage = "¡Feliz año y medio juntos mi amor! 18 meses de felicidad absoluta.",
            fullContent = """
¡Feliz año y medio, amor de mi vida!
Dieciocho meses de caminar juntos. Hemos aprendido tanto el uno del otro, hemos compartido confidencias y nos hemos demostrado que cuando hay amor sincero, cualquier distancia o dificultad se supera. Te amo con toda mi alma.
            """.trimIndent(),
            romanticQuote = "Un año y medio construyendo el paraíso en la tierra a través de tus abrazos."
        ),
        MonthlyLetter(
            monthNumber = 24,
            dateLabel = "10 de Noviembre de 2025",
            title = "¡2 AÑOS JUNTOS! Dos vueltas al sol amándote",
            notificationMessage = "¡FELICES 2 AÑOS MI AMOR! 💖 730 días de la historia más hermosa.",
            fullContent = """
¡¡FELICES DOS AÑOS, MI REINA PRECIOSA!! 💖
Hoy celebramos 730 días desde aquel 10 de noviembre de 2023. Dos años completos en los que has sido mi pilar, mi musa y mi felicidad entera.

Ver cómo nuestro amor ha madurado, cómo seguimos cuidándonos y cómo la ternura sigue viva en cada abrazo me hace sentir el hombre más bendecido del planeta. Te prometo que este amor seguirá creciendo con los años, porque mi corazón te pertenece por completo.
¡Felices dos años, mi vida!
            """.trimIndent(),
            romanticQuote = "Dos años contigo me han enseñado que la eternidad a tu lado será corta."
        ),
        MonthlyLetter(
            monthNumber = 30,
            dateLabel = "10 de Mayo de 2026",
            title = "Mes 30: Dos años y medio de complicidad",
            notificationMessage = "¡Felices 30 meses mi amor! Dos años y medio caminando juntos.",
            fullContent = """
¡Felices 30 meses, mi tesoro!
Dos años y medio de mirarte a los ojos y confirmar que tomar tu mano aquel 10 de noviembre fue la mejor decisión que he tomado en mi vida. Gracias por tanto amor, por tu bondad y por tu fe en nosotros.
            """.trimIndent(),
            romanticQuote = "Treinta meses donde tu amor ha sido mi faro y mi paz."
        ),
        MonthlyLetter(
            monthNumber = 35,
            dateLabel = "10 de Octubre de 2026",
            title = "Mes 35: A un mes de nuestro tercer año",
            notificationMessage = "¡Felices 35 meses mi amor! 35 meses de devoción y amor infinito.",
            fullContent = """
¡Feliz mes 35, mi reina hermosa!
Casi tres años juntos. Más de mil días de historias, de miradas cómplices y de un cariño que no para de crecer. Gracias por ser mi mayor bendición cada día.
            """.trimIndent(),
            romanticQuote = "El tiempo a tu lado no se mide en meses, sino en latidos llenos de felicidad."
        ),
        MonthlyLetter(
            monthNumber = 36,
            dateLabel = "10 de Noviembre de 2026",
            title = "¡3 AÑOS JUNTOS! Tres años de amor infinito",
            notificationMessage = "¡FELICES 3 AÑOS MI AMOR! 💖 Tres años de mi mayor felicidad.",
            fullContent = """
¡¡FELICES TRES AÑOS, AMOR DE MI VIDA!! 💖
Tres años exactos desde el 10 de noviembre de 2023. Más de mil noventa y cinco días eligiéndote sin dudar ni un segundo.

Hemos construido un amor maduro, leal, divertido y profundamente arraigado. Gracias por ser la mujer más extraordinaria que existe. Este contador sigue marcando hacia el infinito porque mi amor por ti no tiene límites ni final.
¡Te amo con toda mi alma, hoy y siempre!
            """.trimIndent(),
            romanticQuote = "Tres años confirmando que el amor de mi vida tiene tu nombre y tus ojos."
        )
    )

    fun getMonthlyLetter(monthNumber: Int): MonthlyLetter {
        val found = MONTHLY_LETTERS_LIST.find { it.monthNumber == monthNumber }
        if (found != null) return found

        // Generate dynamically for any month number up to infinity!
        val cal = LoveTimeTracker.getStartDateCalendar()
        cal.add(Calendar.MONTH, monthNumber)
        val sdf = SimpleDateFormat("10 'de' MMMM 'de' yyyy", Locale("es", "ES"))
        val dateStr = sdf.format(cal.time)

        val yearsTogether = monthNumber / 12
        val extraMonths = monthNumber % 12
        val timeLabel = when {
            yearsTogether > 0 && extraMonths == 0 -> "¡$yearsTogether AÑOS JUNTOS!"
            yearsTogether > 0 -> "$yearsTogether años y $extraMonths meses"
            else -> "Mes $monthNumber"
        }

        return MonthlyLetter(
            monthNumber = monthNumber,
            dateLabel = dateStr,
            title = "Aniversario: $timeLabel",
            notificationMessage = "¡Feliz aniversario mi amor! ($timeLabel). Te amo infinitamente hoy y siempre.",
            fullContent = """
¡Feliz aniversario, amor de mi vida! ($timeLabel)
Hoy es 10 de mes y nuestro contador sigue avanzando hacia el infinito. Ya son $monthNumber meses desde aquel 10 de noviembre de 2023 donde comenzó nuestro viaje de amor.

Cada mes que cumplimos renuevo mi promesa de cuidarte, respetarte y hacerte sonreír todos los días. Gracias por ser mi compañera incondicional, mi hogar y mi mayor tesoro.

¡Te amo con toda mi alma, mi reina hermosa!
            """.trimIndent(),
            romanticQuote = "$monthNumber meses sumando momentos y restando distancias en nuestro amor infinito."
        )
    }

    fun getAllMonthlyLetters(upToMonth: Int = 48): List<MonthlyLetter> {
        val list = mutableListOf<MonthlyLetter>()
        val maxMonth = upToMonth.coerceAtLeast(36)
        for (m in 1..maxMonth) {
            list.add(getMonthlyLetter(m))
        }
        return list
    }

    val REASONS_WHY_I_LOVE_YOU = listOf(
        "Por la forma en que tus ojos brillan cuando me sonríes.",
        "Por la paz inmensa que siento cada vez que me abrazas.",
        "Por tu ternura infinita incluso en los días más complicados.",
        "Por tu risa contagiosa que ilumina cualquier habitación.",
        "Porque a tu lado puedo ser 100% yo mismo sin ningún miedo.",
        "Por la complicidad única que tenemos con solo mirarnos.",
        "Por cómo crees en mí y me impulsas a ser mejor cada día.",
        "Por tu voz dulce cuando me dices 'te amo'.",
        "Por cómo cuidas de mí y te preocupas por cada pequeño detalle.",
        "Por los besos inesperados que me llenan de alegría el alma.",
        "Por tu sentido del humor y por hacerme reír como nadie más.",
        "Por ser mi mejor amiga, mi confidente y el amor de mi vida.",
        "Por tu paciencia y la sabiduría con la que conversamos.",
        "Por hacer que cualquier lugar del mundo sea un hogar si estoy contigo.",
        "Por los recuerdos hermosos que hemos construido desde el 10 de noviembre del 2023.",
        "Por tu nobleza y la pureza de tu hermoso corazón.",
        "Por ser la niña de mis ojos y la reina de todos mis pensamientos.",
        "Por enseñarme lo que significa el amor verdadero y correspondido.",
        "Por los abrazos largos que no quiero que terminen jamás.",
        "Por elegirme todos los días, así como yo te elijo a ti infinitamente."
    )

    val LOVE_PROMISES = listOf(
        "Prometo estar a tu lado en los días soleados y en las tormentas más difíciles.",
        "Prometo escucharte siempre con el corazón abierto y sin juzgarte.",
        "Prometo hacerte reír todos los días y secar cada una de tus lágrimas.",
        "Prometo recordarte lo hermosa, valiosa y única que eres para mí.",
        "Prometo cuidar nuestro amor como el tesoro más preciado de mi vida.",
        "Prometo seguir tomándote de la mano hasta que seamos viejitos."
    )

    // Local Custom Letters storage
    private const val PREFS_NAME = "bm_love_letters_prefs"
    private const val KEY_CUSTOM_LETTERS = "custom_letters_json"

    fun loadCustomLetters(context: Context): List<CustomLetter> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY_CUSTOM_LETTERS, null) ?: return emptyList()
        val list = mutableListOf<CustomLetter>()
        try {
            val array = JSONArray(json)
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                list.add(
                    CustomLetter(
                        id = obj.getString("id"),
                        title = obj.getString("title"),
                        date = obj.getString("date"),
                        content = obj.getString("content")
                    )
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }

    fun saveCustomLetter(context: Context, letter: CustomLetter) {
        val current = loadCustomLetters(context).toMutableList()
        current.removeAll { it.id == letter.id }
        current.add(0, letter)
        val array = JSONArray()
        for (item in current) {
            val obj = JSONObject()
            obj.put("id", item.id)
            obj.put("title", item.title)
            obj.put("date", item.date)
            obj.put("content", item.content)
            array.put(obj)
        }
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_CUSTOM_LETTERS, array.toString()).apply()
    }
}

import kotlin.math.ceil
import kotlin.system.exitProcess


const val NOME_HOTEL = "Hotel Morning Star"
const val SENHA_CORRETA = "2678"
val quartos = arrayOfNulls<String>(20)
val listaHospedes = mutableListOf<String>()


fun main() {
    println("Bem-vindo ao $NOME_HOTEL")
    println("------------------------------------")

    print("Por favor, digite seu nome de usuário: ")
    val nomeUsuario = readlnOrNull() ?: "Funcionário"

    print("Digite sua senha: ")
    val senhaDigitada = readlnOrNull()

    if (senhaDigitada == SENHA_CORRETA) {
        inicio(nomeUsuario)
    } else {
        println("Senha incorreta. O sistema será encerrado.")
    }
}


fun inicio(nomeUsuario: String) {
    println("\nBem-vindo ao Hotel $NOME_HOTEL, $nomeUsuario. É um imenso prazer ter você por aqui!")

    while (true) {
        exibirMenu()
        print("Escolha uma opção: ")
        val opcao = readlnOrNull()?.toIntOrNull()

        when (opcao) {
            1 -> reservarQuarto(nomeUsuario)
            2 -> cadastrarHospedesComDesconto(nomeUsuario)
            3 -> gerenciarCadastroHospedes()
            4 -> reservarEvento(nomeUsuario)
            5 -> compararCombustivel(nomeUsuario)
            6 -> orcamentoArCondicionado(nomeUsuario)
            7 -> {
                println("\nMuito obrigado e até logo, $nomeUsuario.")
                exitProcess(0)
            }
            else -> exibirErro()
        }
    }
}


fun exibirMenu() {
    println("\n========== MENU PRINCIPAL ==========")
    println("1. Reservar Quarto")
    println("2. Cadastrar Hóspedes por Idade")
    println("3. Gerenciar Cadastro de Hóspedes")
    println("4. Reservar Evento")
    println("5. Verificar Combustível")
    println("6. Orçamento de Manutenção")
    println("7. Sair do Sistema")
    println("====================================")
}

fun exibirErro() {
    println("\nOpção inválida. Por favor, escolha uma opção entre 1 e 7.")
}

fun reservarQuarto(nomeUsuario: String) {
    println("\n--- Módulo de Reserva de Quartos ---")

    print("Qual o valor padrão da diária? R$ ")
    val diaria = readlnOrNull()?.toDoubleOrNull()
    if (diaria == null || diaria < 0) {
        println("Valor inválido, $nomeUsuario")
        return
    }

    print("Quantas diárias serão necessárias? ")
    val dias = readlnOrNull()?.toIntOrNull()
    if (dias == null || dias <= 0 || dias > 30) {
        println("Valor inválido, $nomeUsuario")
        return
    }

    val total = diaria * dias
    println("O valor de $dias dias de hospedagem é de R$${"%.2f".format(total)}")

    print("Qual o nome do hóspede? ")
    val nomeHospede = readlnOrNull() ?: "Hóspede Anônimo"

    var numQuarto: Int
    while (true) {
        print("Qual o quarto para reserva? (1 - 20)? ")
        numQuarto = readlnOrNull()?.toIntOrNull() ?: 0

        if (numQuarto in 1..20) {
            if (quartos[numQuarto - 1] == null) {
                println("Quarto Livre.")
                break
            } else {
                println("Quarto já está ocupado. Escolha outro.")
                exibirStatusQuartos()
            }
        } else {
            println("Número de quarto inválido. Tente novamente.")
        }
    }

    print("$nomeUsuario, você confirma a hospedagem para $nomeHospede por $dias dias para o quarto $numQuarto por R$${"%.2f".format(total)}? S/N: ")
    val confirmacao = readlnOrNull()

    if (confirmacao.equals("S", ignoreCase = true)) {
        quartos[numQuarto - 1] = nomeHospede
        println("$nomeUsuario, reserva efetuada para $nomeHospede.")
        exibirStatusQuartos()
    } else {
        println("Reserva cancelada.")
    }
}

fun exibirStatusQuartos() {
    println("\n--- Ocupação dos Quartos ---")
    val status = quartos.mapIndexed { index, hospede ->
        "${index + 1}-${if (hospede == null) "livre" else "ocupado por $hospede"}"
    }.joinToString("; ")
    println(status)
}

fun cadastrarHospedesComDesconto(nomeUsuario: String) {
    println("\n--- Módulo de Cadastro de Hóspedes por Idade ---")

    print("Qual o valor padrão da diária? R$ ")
    val diaria = readlnOrNull()?.toDoubleOrNull()
    if (diaria == null || diaria <= 0) {
        println("Valor de diária inválido.")
        return
    }

    var totalHospedagem = 0.0
    var gratuidades = 0
    var meias = 0

    while (true) {
        print("Qual o nome do Hóspede? (Digite 'PARE' para finalizar): ")
        val nome = readlnOrNull() ?: ""

        if (nome.equals("PARE", ignoreCase = true)) {
            break
        }

        print("Qual a idade de $nome? ")
        val idade = readlnOrNull()?.toIntOrNull()

        if (idade == null || idade < 0) {
            println("Idade inválida. Tente novamente.")
            continue
        }

        print("$nome cadastrada(o) com sucesso. ")
        when {
            idade < 6 -> {
                gratuidades++
                println("$nome possui gratuidade.")
            }
            idade > 60 -> {
                meias++
                totalHospedagem += diaria / 2
                println("$nome paga meia.")
            }
            else -> {
                totalHospedagem += diaria
                println() // Apenas para pular linha
            }
        }
    }
    println("\n$nomeUsuario, o valor total das hospedagens é: R$${"%.2f".format(totalHospedagem)}; $gratuidades gratuidade(s); $meias meia(s)")
}

fun gerenciarCadastroHospedes() {
    println("\n--- Módulo de Gerenciamento de Hóspedes ---")

    while (true) {
        println("\nSelecione uma opção: 1. Cadastrar | 2. Pesquisar | 3. Listar | 4. Voltar ao menu principal")
        print("Opção: ")
        when (readlnOrNull()?.toIntOrNull()) {
            1 -> {
                if (listaHospedes.size >= 15) {
                    println("Máximo de cadastros atingido.")
                } else {
                    print("Qual o nome do Hóspede? ")
                    val nome = readlnOrNull() ?: ""
                    if(nome.isNotBlank()){
                        listaHospedes.add(nome)
                        println("Hóspede \"$nome\" foi cadastrada(o) com sucesso!")
                    }
                }
            }
            2 -> {
                print("Qual o nome do Hóspede para pesquisar? ")
                val nome = readlnOrNull() ?: ""
                if (listaHospedes.contains(nome)) {
                    println("Hóspede $nome foi encontrada(o)!")
                } else {
                    println("Hóspede $nome não foi encontrada(o)!")
                }
            }
            3 -> {
                if (listaHospedes.isEmpty()) {
                    println("Nenhum hóspede cadastrado.")
                } else {
                    println("\n--- Lista de Hóspedes Cadastrados ---")
                    listaHospedes.forEach { println("- $it") }
                }
            }
            4 -> return
            else -> println("Opção inválida.")
        }
    }
}


fun reservarEvento(nomeUsuario: String) {
    println("\n--- Módulo de Reserva de Eventos ---")

    // Parte 1: Auditório
    print("Qual o número de convidados para o seu evento? ")
    val convidados = readlnOrNull()?.toIntOrNull() ?: 0

    val auditorio: String
    if (convidados <= 0 || convidados > 350) {
        println("Número de convidados inválido.")
        return
    } else if (convidados <= 150) {
        auditorio = "Laranja"
        println("Use o auditório $auditorio.")
    } else if (convidados <= 220) {
        auditorio = "Laranja"
        println("Use o auditório $auditorio (inclua mais ${convidados - 150} cadeiras).")
    } else {
        auditorio = "Colorado"
        println("Use o auditório $auditorio.")
    }

    // Parte 2: Agenda
    print("Qual o dia do evento (ex: segunda, sabado)? ")
    val diaSemana = readlnOrNull()?.lowercase() ?: ""

    print("Qual a hora do evento (7-23)? ")
    val hora = readlnOrNull()?.toIntOrNull() ?: 0

    val disponivel = when (diaSemana) {
        "segunda", "terca", "quarta", "quinta", "sexta" -> hora in 7..23
        "sabado", "domingo" -> hora in 7..15
        else -> false
    }

    if (!disponivel) {
        println("Auditório indisponível neste dia/horário.")
        return
    }

    print("Qual o nome da empresa? ")
    val nomeEmpresa = readlnOrNull() ?: "Empresa Anônima"
    println("Auditório reservado para $nomeEmpresa. $diaSemana às ${hora}hs.")

    // Parte 3: Garçons
    print("Qual a duração do evento em horas? ")
    val duracao = readlnOrNull()?.toIntOrNull() ?: 0
    if(duracao <= 0) {
        println("Duração inválida.")
        return
    }

    val garconsPorConvidados = ceil(convidados / 12.0).toInt()
    val garconsPorHoras = duracao / 2
    val totalGarcons = garconsPorConvidados + garconsPorHoras
    val custoGarcons = totalGarcons * 10.50 * duracao
    println("São necessários $totalGarcons garçons.")
    println("Custo: R$ ${"%.2f".format(custoGarcons)}")


    // Parte 4: Buffet e Confirmação
    val cafeLitros = convidados * 0.2
    val aguaLitros = convidados * 0.5
    val salgadosTotal = convidados * 7
    println("\nO evento precisará de ${"%.1f".format(cafeLitros)} litros de café, ${"%.1f".format(aguaLitros)} litros de água, $salgadosTotal salgados.")

    val custoCafe = cafeLitros * 0.80
    val custoAgua = aguaLitros * 0.40
    val custoSalgados = ceil(salgadosTotal / 100.0) * 34.00
    val custoBuffet = custoCafe + custoAgua + custoSalgados
    val custoTotalEvento = custoGarcons + custoBuffet

    println("\n========== RELATÓRIO DO EVENTO ==========")
    println("Evento no Auditório: $auditorio")
    println("Nome da Empresa: $nomeEmpresa")
    println("Data: $diaSemana, ${hora}H às ${hora + duracao}H")
    println("Duração do evento: ${duracao}H")
    println("Quantidade de Convidados: $convidados")
    println("Quantidade de garçons: $totalGarcons")
    println("-------------------------------------------")
    println("Custo dos garçons: R$ ${"%.2f".format(custoGarcons)}")
    println("Custo do Buffet: R$ ${"%.2f".format(custoBuffet)}")
    println("-------------------------------------------")
    println("Valor total do Evento: R$ ${"%.2f".format(custoTotalEvento)}")
    println("==========================================")

    print("\nGostaria de efetuar a reserva? S/N: ")
    if (readlnOrNull().equals("S", ignoreCase = true)) {
        println("$nomeUsuario, reserva efetuada com sucesso.")
    } else {
        println("Reserva não efetuada.")
    }
}


fun compararCombustivel(nomeUsuario: String) {
    println("\n--- Módulo de Comparação de Combustível ---")
    val tanque = 42.0

    print("Qual o valor do álcool no posto Wayne Oil? R$ ")
    val alcoolWayne = readlnOrNull()?.toDoubleOrNull() ?: 0.0
    print("Qual o valor da gasolina no posto Wayne Oil? R$ ")
    val gasWayne = readlnOrNull()?.toDoubleOrNull() ?: 0.0

    print("Qual o valor do álcool no posto Stark Petrol? R$ ")
    val alcoolStark = readlnOrNull()?.toDoubleOrNull() ?: 0.0
    print("Qual o valor da gasolina no posto Stark Petrol? R$ ")
    val gasStark = readlnOrNull()?.toDoubleOrNull() ?: 0.0

    val custoWayne = if (alcoolWayne <= gasWayne * 0.7) alcoolWayne * tanque else gasWayne * tanque
    val combustivelWayne = if (alcoolWayne <= gasWayne * 0.7) "álcool" else "gasolina"

    val custoStark = if (alcoolStark <= gasStark * 0.7) alcoolStark * tanque else gasStark * tanque
    val combustivelStark = if (alcoolStark <= gasStark * 0.7) "álcool" else "gasolina"

    if (custoWayne < custoStark) {
        println("$nomeUsuario, é mais barato abastecer com $combustivelWayne no posto Wayne Oil.")
    } else {
        println("$nomeUsuario, é mais barato abastecer com $combustivelStark no posto Stark Petrol.")
    }
}


fun orcamentoArCondicionado(nomeUsuario: String) {
    println("\n--- Módulo de Orçamento de Manutenção ---")
    var menorValor = Double.MAX_VALUE
    var melhorEmpresa = ""

    do {
        print("\nQual o nome da empresa? ")
        val nomeEmpresa = readlnOrNull() ?: "Empresa Desconhecida"
        print("Qual o valor por aparelho? R$ ")
        val valor = readlnOrNull()?.toDoubleOrNull() ?: 0.0
        print("Qual a quantidade de aparelhos? ")
        val qtd = readlnOrNull()?.toIntOrNull() ?: 0
        print("Qual a porcentagem de desconto? % ")
        val desconto = readlnOrNull()?.toDoubleOrNull() ?: 0.0
        print("Qual o número mínimo de aparelhos para o desconto? ")
        val minQtd = readlnOrNull()?.toIntOrNull() ?: Int.MAX_VALUE

        var custoTotal = valor * qtd
        if (qtd >= minQtd) {
            custoTotal *= (1 - desconto / 100.0)
        }

        println("O serviço de $nomeEmpresa custará R$ ${"%.2f".format(custoTotal)}")

        if (custoTotal < menorValor) {
            menorValor = custoTotal
            melhorEmpresa = nomeEmpresa
        }

        print("Deseja informar novos dados, $nomeUsuario? (S/N): ")
        val continuar = readlnOrNull()
    } while (continuar.equals("S", ignoreCase = true))

    if (melhorEmpresa.isNotBlank()) {
        println("\nO orçamento de menor valor é o de $melhorEmpresa por R$ ${"%.2f".format(menorValor)}")
    }
}
# sifuture

Jogo desenvolvido por Renan Franca em **2006**, em Java ME (J2ME), exclusivamente como material para estudos de programação e desenvolvimento de jogos para celulares.

Este repositório preserva o projeto como registro histórico. O jogo foi criado sem qualquer intenção de utilização para fins comerciais.

## Vídeo do jogo

[Assista ao sifuture rodando no YouTube](https://youtu.be/1xMKYEy7Jqw?si=oF48Zq7EeNTLTb3J).

## Java e ambiente original

O jogo é um MIDlet Java ME. Os descritores e arquivos do projeto registram:

| Componente | Versão ou configuração |
| --- | --- |
| Plataforma | Java ME / J2ME |
| Configuração de execução | CLDC 1.1 |
| Perfil de execução | MIDP 2.0 |
| Compatibilidade do compilador Eclipse | Java 1.3 |
| Alvo de bytecode configurado | Java 1.1 |
| Bytecode inspecionado em `bin/Midlet.class` | Versão 45.3 (Java 1.1) |
| Integração com Eclipse | EclipseME; metadados na versão 1.5.0 |
| SDK registrado no projeto | Motorola Java ME SDK v6.4 for Motorola OS Products |
| Dispositivo configurado | MOTOKRZR K1 |

A versão exata do JDK utilizado originalmente não está registrada. A compatibilidade Java 1.3 e o alvo Java 1.1 são configurações de compilação, enquanto CLDC 1.1 e MIDP 2.0 identificam o ambiente exigido pelo jogo.

Há uma configuração legada de pré-verificação CLDC 1.0 nas preferências do EclipseME; os descritores `.jad` e o manifesto do `.jar` declaram CLDC 1.1, usado como requisito de execução nesta documentação.

## Como executar

É necessário um celular ou emulador Java ME compatível com **CLDC 1.1 e MIDP 2.0**. Importe ou instale o par `deployed/Sifuture.jad` e `deployed/Sifuture.jar` no ambiente compatível, mantendo os arquivos juntos.

Um JDK moderno sozinho não fornece as APIs `javax.microedition` e não executa esse MIDlet diretamente com `java -jar`.

Os arquivos de distribuição são históricos. A execução e a recompilação não foram validadas nesta publicação. Para recompilar, é necessário configurar um ambiente Java ME e as bibliotecas do SDK; o projeto não possui uma configuração moderna de Maven ou Gradle.

## Estrutura do projeto

- `src/`: código-fonte Java e imagens históricas.
- `res/`: recursos do projeto.
- `deployed/`: arquivos históricos de distribuição `.jar` e `.jad`.
- `doc/`: documentação Javadoc original.
- `.classpath`, `.project`, `.eclipseme` e `.settings/`: configurações originais do Eclipse e EclipseME.
- `javadoc.xml`: configuração histórica de geração da documentação, com caminhos locais do ambiente original.

As configurações foram preservadas como registro histórico e podem exigir ajustes para outro computador. Classes compiladas intermediárias e bancos temporários do emulador não são versionados.

## Aviso sobre as imagens e finalidade de estudos

**As imagens utilizadas neste jogo pertencem a outros jogos e aos seus respectivos titulares.** Elas foram encontradas publicamente na internet e utilizadas exclusivamente como material para estudos neste projeto.

Não reivindico autoria ou propriedade sobre essas imagens. O desenvolvimento do jogo em 2006 teve finalidade educacional, sem qualquer intenção de utilização para fins comerciais ou de associação com os titulares dos jogos originais.

As imagens de terceiros **não estão abrangidas pela Apache License 2.0**, inclusive quando incorporadas ao `.jar`. Este repositório não concede direitos de reutilização dessas imagens nem afirma autorização para sua redistribuição. O fato de terem sido encontradas publicamente na internet não implica uma licença de uso ou redistribuição.

## Licença do código

O código de autoria de Renan Franca é disponibilizado sob a **Apache License 2.0**. Consulte [LICENSE](LICENSE) e [NOTICE](NOTICE).

A declaração de finalidade educacional e ausência de intenção comercial descreve o propósito original do projeto. Ela não acrescenta uma proibição de uso comercial ao código licenciado: os usos desse código seguem os termos da Apache License 2.0. As imagens de terceiros permanecem excluídas dessa licença.

# sifuture

[English](#english) | [Português (Brasil) — pt-BR](#português-brasil--pt-br)

## English

A game developed by Renan Franca in **2006**, using Java ME (J2ME), exclusively as learning material for programming and mobile game development.

This repository preserves the project as a historical record. The game was created without any intention of commercial use.

### Gameplay rules

sifuture is a spaceship shooter created to address something that always frustrated me about this kind of game: losing all the powers I had collected whenever I died. Here, losing a life takes away only one upgrade level of your weapons, preserving part of your accumulated progress.

The goal is the classic one: reach the end of the stage without running out of lives and aim for the highest score possible.

The stage features a miniboss and a boss, as in classic games of the genre. When the boss is low on health, it starts flashing, fires more rapidly, and uses a special attack, making the battle more intense.

At the end of the stage, you receive a message based on your score, such as "OK", "Good", or "I Can't Believe".

### Gameplay video

[Watch sifuture running on YouTube](https://youtu.be/1xMKYEy7Jqw?si=oF48Zq7EeNTLTb3J).

### Java and the original environment

The game is a Java ME MIDlet. The descriptors and project files record:

| Component | Version or configuration |
| --- | --- |
| Platform | Java ME / J2ME |
| Runtime configuration | CLDC 1.1 |
| Runtime profile | MIDP 2.0 |
| Eclipse compiler compliance | Java 1.3 |
| Configured bytecode target | Java 1.1 |
| Bytecode inspected in `bin/Midlet.class` | Version 45.3 (Java 1.1) |
| Eclipse integration | EclipseME; metadata version 1.5.0 |
| SDK recorded in the project | Motorola Java ME SDK v6.4 for Motorola OS Products |
| Configured device | MOTOKRZR K1 |

The exact JDK version originally used is not recorded. Java 1.3 compliance and the Java 1.1 target are compilation settings, while CLDC 1.1 and MIDP 2.0 identify the environment required by the game.

The EclipseME preferences contain a legacy CLDC 1.0 preverification setting; the `.jad` descriptors and `.jar` manifest declare CLDC 1.1, which is the runtime requirement used in this documentation.

### How to run

A Java ME phone or emulator compatible with **CLDC 1.1 and MIDP 2.0** is required. Import or install the `deployed/Sifuture.jad` and `deployed/Sifuture.jar` pair in a compatible environment, keeping both files together.

A modern JDK alone does not provide the `javax.microedition` APIs and cannot run this MIDlet directly with `java -jar`.

The distribution files are historical artifacts. Running and rebuilding the game were not validated for this publication. Rebuilding requires a configured Java ME environment and the SDK libraries; the project has no modern Maven or Gradle configuration.

### Project structure

- `src/`: Java source code and historical images.
- `res/`: project resources.
- `deployed/`: historical `.jar` and `.jad` distribution files.
- `doc/`: original Javadoc documentation.
- `.classpath`, `.project`, `.eclipseme`, and `.settings/`: original Eclipse and EclipseME settings.
- `javadoc.xml`: historical documentation generation configuration, with local paths from the original environment.

The settings have been preserved as a historical record and may need adjustments for another computer. Intermediate compiled classes and temporary emulator databases are not tracked in version control.

### Notice about images and educational purpose

**The images used in this game belong to other games and their respective rights holders.** They were found publicly on the internet and used exclusively as learning material in this project.

I do not claim authorship or ownership of these images. The game's development in 2006 had an educational purpose, without any intention of commercial use or affiliation with the rights holders of the original games.

Third-party images **are not covered by the Apache License 2.0**, including when embedded in the `.jar`. This repository does not grant rights to reuse these images or claim permission to redistribute them. Being publicly available on the internet does not imply a license to use or redistribute them.

### Code license

The code authored by Renan Franca is available under the **Apache License 2.0**. See [LICENSE](LICENSE) and [NOTICE](NOTICE).

The statement about educational purpose and lack of commercial intent describes the project's original purpose. It does not add a restriction on commercial use of the licensed code: use of that code is governed by the Apache License 2.0. Third-party images remain excluded from that license.

---

## Português (Brasil) — pt-BR

Jogo desenvolvido por Renan Franca em **2006**, em Java ME (J2ME), exclusivamente como material para estudos de programação e desenvolvimento de jogos para celulares.

Este repositório preserva o projeto como registro histórico. O jogo foi criado sem qualquer intenção de utilização para fins comerciais.

### Regras do jogo

sifuture é um jogo de nave criado para superar algo que sempre me incomodou nesse tipo de jogo: perder todos os poderes que eu havia acumulado ao morrer. Aqui, ao perder uma vida, você perde apenas um nível de evolução do armamento, preservando parte do progresso acumulado.

O objetivo é o clássico: chegar ao final da fase sem gastar todas as vidas e buscar a maior pontuação possível.

A fase tem um subchefe e um chefe, como nos jogos clássicos do gênero. Quando o chefe fica com pouca vida, começa a piscar, dispara mais rapidamente e utiliza um ataque especial, tornando a batalha mais intensa.

Ao final da fase, você recebe uma mensagem de acordo com sua pontuação, como "OK", "Good" ou "I Can't Believe".

### Vídeo do jogo

[Assista ao sifuture rodando no YouTube](https://youtu.be/1xMKYEy7Jqw?si=oF48Zq7EeNTLTb3J).

### Java e ambiente original

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

### Como executar

É necessário um celular ou emulador Java ME compatível com **CLDC 1.1 e MIDP 2.0**. Importe ou instale o par `deployed/Sifuture.jad` e `deployed/Sifuture.jar` no ambiente compatível, mantendo os arquivos juntos.

Um JDK moderno sozinho não fornece as APIs `javax.microedition` e não executa esse MIDlet diretamente com `java -jar`.

Os arquivos de distribuição são históricos. A execução e a recompilação não foram validadas nesta publicação. Para recompilar, é necessário configurar um ambiente Java ME e as bibliotecas do SDK; o projeto não possui uma configuração moderna de Maven ou Gradle.

### Estrutura do projeto

- `src/`: código-fonte Java e imagens históricas.
- `res/`: recursos do projeto.
- `deployed/`: arquivos históricos de distribuição `.jar` e `.jad`.
- `doc/`: documentação Javadoc original.
- `.classpath`, `.project`, `.eclipseme` e `.settings/`: configurações originais do Eclipse e EclipseME.
- `javadoc.xml`: configuração histórica de geração da documentação, com caminhos locais do ambiente original.

As configurações foram preservadas como registro histórico e podem exigir ajustes para outro computador. Classes compiladas intermediárias e bancos temporários do emulador não são versionados.

### Aviso sobre as imagens e finalidade de estudos

**As imagens utilizadas neste jogo pertencem a outros jogos e aos seus respectivos titulares.** Elas foram encontradas publicamente na internet e utilizadas exclusivamente como material para estudos neste projeto.

Não reivindico autoria ou propriedade sobre essas imagens. O desenvolvimento do jogo em 2006 teve finalidade educacional, sem qualquer intenção de utilização para fins comerciais ou de associação com os titulares dos jogos originais.

As imagens de terceiros **não estão abrangidas pela Apache License 2.0**, inclusive quando incorporadas ao `.jar`. Este repositório não concede direitos de reutilização dessas imagens nem afirma autorização para sua redistribuição. O fato de terem sido encontradas publicamente na internet não implica uma licença de uso ou redistribuição.

### Licença do código

O código de autoria de Renan Franca é disponibilizado sob a **Apache License 2.0**. Consulte [LICENSE](LICENSE) e [NOTICE](NOTICE).

A declaração de finalidade educacional e ausência de intenção comercial descreve o propósito original do projeto. Ela não acrescenta uma proibição de uso comercial ao código licenciado: os usos desse código seguem os termos da Apache License 2.0. As imagens de terceiros permanecem excluídas dessa licença.

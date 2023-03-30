# SI-Project
## Trabalho final da cadiera de Segurança Informática
### XIUUU: Troca de Segredos Criptográﬁcos Seguro

O objetivo principal deste trabalho é implementar um sistema que permita trocar segredos
criptográﬁcos (só as chaves) entre duas entidades de uma forma ﬁável e segura. A ideia é
que o sistema integre e disponibilize uma série de esquemas de distribuição e protocolos
de acordo de chaves criptográﬁcas, e também formas de as gerar a partir de palavras-
passe. Em princípio, o sistema deve poder ser concretizado numa única aplicação que,
depois de executada em dois computadores ou dispositivos distintos, permita a geração
e troca de segredos entre ambas as instâncias. Entre outras a pensar, o sistema deve
fornecer as seguintes funcionalidades base:
- geração de um segredo criptográﬁco a partir de palavras-passe inseridas pelo utili-
zador, nomeadamente através de algoritmos como o Password Based Key Derivation
Function 2 (PBKDF2); (Miguel)
- troca de um segredo criptográﬁco usando o protocolo de acordo de chaves Difﬁe-
Hellman;(Dani)
- troca de um segredo criptográﬁco usando Puzzles de Merkle; (Simão)
- troca de um segredo criptográﬁco usando o Rivest, Shamir e Adleman (RSA);(Pedro)
- distribuição de novas chaves de cifra a partir de chaves pré-distribuídas;
- distribuição de novas chaves de cifra usando um agente de conﬁança (neste caso, a
aplicação desenvolvida deve permitir que uma das instâncias possa ser conﬁgurada
como agente de conﬁança);
- implementar forma de ter a certeza de que o segredo partilhado é o mesmo dos dois
lados. (calculo do hash)

A aplicação desenvolvida pode funcionar em modo Client Line Interface (CLI) ou fornecer
uma Graphical User Interface (GUI)(Fábio). Eventualmente, este sistema pode ser implementado
para dispositivos móveis, nomeadamente para a plataforma Android. Conforme já suge-
rido em cima, a aplicação deve poder funcionar em modo cliente ou servidor sendo que,
idealmente, deve deixar que seja o utilizador a escolher o modo sempre que é iniciada.
Caso o modo escolhido seja o de servidor, deve ser então pedida o número da porta em
que vai ﬁcar à escuta; caso contrário, deve ser pedido o endereço Internet Protocol (IP)
e porta do destino. Uma aplicação que esteja a funcionar como servidor deve ser capaz
de fornecer uma lista de utilizadores disponíveis e facultar uma forma de se iniciarem li-
gações dedicadas entre quaisquer dois utilizadores para posterior troca de segredos. O
trabalho e conhecimento podem ser fortalecidos através da implementação das seguintes
funcionalidades:
- usar certiﬁcados digitais X.509 nas trocas de segredos que recorrem ao RSA;
- implementar uma infraestrutura de chave pública para o sistema e validar cadeias de
certiﬁcados nas trocas de segredos que recorrem ao RSA (e.g., deﬁnir um certiﬁcado
raiz para o sistema e que já vem embutido no código ou com a aplicação, gerando
depois certiﬁcados digitais para cada um dos utilizadores do sistema);
- pensar numa forma correta de fornecer certiﬁcados digitais a utilizadores;
- implementar mecanismos de assinatura digital para veriﬁcação de integridade em tro-
cas de chave efémeras usando o Difﬁe-Hellman;
- possibilitar a escolha de diferentes algoritmos de cifra para os Puzzles de Merkle;
- possibilitar a escolha de diferentes funções de hash para o PBKDF2;
- ter um help bastante completo e ser de simples utilização.

Pensem numa forma de atacar o sistema (uma falha da sua implementação) e dediquem-
lhe um pequeno intervalo de tempo na apresentação. Notem que, para efeitos de avaliação
e prototipagem, o sistema desenvolvido pode executar localmente todos os seus compo-
nentes/aplicações/programas, desde que simule ou concretize a arquitetura sugerida (i.e.,
não precisa necessariamente executar em rede).

Enuciado fornecido pelo: Professor Doutor Pedro Inácio

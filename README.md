# Positional Strings

A biblioteca de strings posisionais, Positional Strings, foi desenvolvida com o intuito de ser um componente de código aberto e gratuito pra ser integrado ao seu sistema escrito em Java. A biblioteca converte uma string posicional para objetos, e converte objetos e uma string posicional, conforme o uso correto da anotaçao @Positional.

A biblioteca foi escrita agnostica a fremework, podendo ser usada como componente nos mais robustos fremeworks ativos no mercado, desde que a versão do Java no projeto seja 11 ou superior. **Esse componente não funcionará em versões anteriores ao Java 11.**

# Instalação

``` 
<dependency>
    <groupId>io.github.amdb-dev</groupId>
    <artifactId>positional-strings</artifactId>
    <version>1.0.0</version>
</dependency>
```

# Uso
Para fazer o uso correto da biblioteca, siga sua implementaçao conforme o modelo abaixo.
## Parametros da anotaçao
* **length** - tamanho da string a ser gerada ou convertido para objeto;
* **filler** - tipo de alinhamento podendo ser: SPACES_RIGTH (espaços a direita), SPACES_LEFT (espaços a esquerda), ZEROS_RIGTH (zeros a direita), ZEROS_LEFT (zeros a esquerda), caso nao seja informado o modelo de alinhamento para geraçao, sera usado como padrao o NONE (sem alinhamento);
* **collectionSize** - tamanho esperado da lista passada;
* **separator** - separador de itens, caso nao informado, sera assumido o sem separador;

## Tipos de dados suportados
* String;
* Integer;
* Double;
* Bigdecimal;
* int;
* double;
* Localdate (YYYY-MM-DD);
* List;
* Set;

Obs: 
* Para os tipos de dados List e Set, o tipo da lista, deve ser especifico, e a classe parametro deve estar anotada com @Positional, nao senod suportados listas dos tipos primitivos ou wrappers;
* O modelo implementado segue padroes de recursividade, podendo encadear listas dentro de listas, e objetos conforme a necessidade, desde que devidamente anotados.



## Objetos para string posicional
```java
public class Foo {
    @Positional(length = 10, filler = Filler.SPACES_RIGTH)
    private String baz;

    @Positional(length = 10, filler = Filler.ZEROS_LEFT)
    private Integer bar;
}

Foo foo = new Foo();
foo.setBaz("teste");
foo.setBar(1);

PositionalConverter positionalConverter = new PositionalConverter();

String transcode = positionalConverter.converter(goo);

//com o modelo implementado acima a saida seria "teste     0000000001"

```
## String posicional para objeto
```java
public class Foo {
    @Positional(length = 10)
    private String baz;

    @Positional(length = 10)
    private Integer bar;
}

PositionalConverter positionalConverter = new PositionalConverter();

Foo foo = positionalConverter.converter("teste     0000000001", Foo.class);

foo.getBaz();//teste
foo.getBar();//1
```
# Considerações finais
A biblioteca é de uso livre e contribuições para melhorias são aceitas.



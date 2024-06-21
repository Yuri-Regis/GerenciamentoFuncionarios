/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.regisdev.gerenciamentofuncionarios;

/**
 *
 * @author yuriregis
 */
import java.math.BigDecimal;
import java.time.LocalDate;
import java.text.NumberFormat;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    public static void main(String[] args) {
        List<Funcionario> funcionarios = new ArrayList<>(Arrays.asList(
            new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"),
            new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"),
            new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"),
            new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"),
            new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"),
            new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"),
            new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"),
            new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"),
            new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"),
            new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente")
        ));

        funcionarios.removeIf(f -> f.getNome().equals("João"));//João removido da lista

        System.out.println("Lista de Funcionários:");
        funcionarios.forEach(System.out::println); //Imprimir a tabela toda

        funcionarios.forEach(f -> f.setSalario(f.getSalario().multiply(new BigDecimal("1.1"))));//Ajustar o salario com 10% de aumento

        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()//Funcao map para agrupar por funcao
            .collect(Collectors.groupingBy(Funcionario::getFuncao));

        System.out.println("\nFuncionários agrupados por suas funções:");
        funcionariosPorFuncao.forEach((funcao, lista) -> {//Imprimir por funcao
            System.out.println("Função: " + funcao);
            lista.forEach(System.out::println);
        });

        System.out.println("\nAniversariantes nos meses 10 e 12:");
        funcionarios.stream()
            .filter(f -> f.getDataNascimento().getMonthValue() == 10 || f.getDataNascimento().getMonthValue() == 12)//Imprimir aniversariantes do mes 10 e 12
            .forEach(System.out::println);

        Funcionario maisVelho = funcionarios.stream()
            .min(Comparator.comparing(Funcionario::getDataNascimento))
            .orElse(null);
        if (maisVelho != null) {
            int idade = Period.between(maisVelho.getDataNascimento(), LocalDate.now()).getYears();//Imprimir funcionario mais velho
            System.out.println("\nFuncionário mais velho: " + maisVelho.getNome() + ", com " + idade + " anos");
        }

        System.out.println("\nFuncionários em ordem alfabética:");
        funcionarios.stream()
            .sorted(Comparator.comparing(Funcionario::getNome))//Imprimir por ordem alfabetica
            .forEach(System.out::println);

        BigDecimal totalSalarios = funcionarios.stream()
            .map(Funcionario::getSalario)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        nf.setGroupingUsed(true);
        System.out.println("\nTotal dos salários somados: " + nf.format(totalSalarios));//Imprimir total dos salarios

        BigDecimal salarioMinimo = new BigDecimal("1212.00");//Salario minimo
        System.out.println("\nQuantos salários mínimos ganha:");
        funcionarios.forEach(f -> {
            BigDecimal quantidadeSalariosMinimos = f.getSalario().divide(salarioMinimo, 2, BigDecimal.ROUND_HALF_UP);//Imprimir quantos salarios minimos ganha
            System.out.println(f.getNome() + " ganha " + quantidadeSalariosMinimos + " salários mínimos.");
        });
    }
}

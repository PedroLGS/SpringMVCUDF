CREATE DATABASE exprod
GO
USE exprod

CREATE TABLE produto (
codigo			INT				NOT NULL,
nome			VARCHAR(30)		NOT NULL,
valor_unitario	DECIMAL(7,2)	NOT NULL,
qtd_estoque		INT				NOT NULL
PRIMARY KEY (codigo)
)

INSERT INTO produto VALUES
(1, 'produto1', 100.00, 30),
(2, 'produto2', 55.30, 45),
(3, 'produto3', 30.00, 55),
(4, 'produto4', 75.00, 40),
(5, 'produto5', 10.50, 100)

SELECT * FROM produto

/*Criar no SQL Server uma database com a seguinte tabela e fazer uma procedure que permita
fazer o insert, update e delete dos dados na tabela, a partir de um código de operação (I, U ou D):
*/

CREATE PROCEDURE sp_produto(@opc CHAR(1), @codigo INT, @nome VARCHAR(30), @valor_unitario DECIMAL(7,2), @qtd_estoque INT, @saida VARCHAR(200) OUTPUT)
AS

	IF (UPPER(@opc) = 'D' AND @codigo IS NOT NULL)
	BEGIN
		DELETE produto WHERE codigo = @codigo
		SET @saida = 'Produto #'+CAST(@codigo AS VARCHAR(4))+' excluído' 
	END
	ELSE
	BEGIN
		IF (UPPER(@opc) = 'D' AND @codigo IS NULL)
		BEGIN
			RAISERROR('Não é possível excluir sem ID', 16, 1)
		END
		ELSE
		BEGIN
				IF (UPPER(@opc) = 'I')
				BEGIN
					INSERT INTO produto VALUES
					(@codigo, @nome, @valor_unitario, @qtd_estoque)
					SET @saida = 'Produto #'+CAST(@codigo AS VARCHAR(4))+' inserido'
				END
				ELSE
				BEGIN
					IF (UPPER(@opc) = 'U')
					BEGIN
						UPDATE produto
						SET nome = @nome, valor_unitario = @valor_unitario,
							qtd_estoque = @qtd_estoque
						WHERE codigo = @codigo
						SET @saida = 'Produto #'+CAST(@codigo AS VARCHAR(4))+' atualizado'
					END
					ELSE
					BEGIN
						RAISERROR('Opção inválida', 16, 1)
					END
				END
			END
		END

DECLARE @out1 VARCHAR(200)
EXEC sp_produto 'U', 7, 'fruta', '50', '50', @out1 OUTPUT
PRINT @out1

/*
Fazer uma Scalar Function que verifique, na tabela produtos (codigo, nome,
valor unitário e qtd estoque) quantos produtos estão com estoque abaixo de
um valor de entrada (O valor mínimo deve ser parâmetro de entrada)*/CREATE FUNCTION fn_valent(@val_min INT)RETURNS INTASBEGIN		DECLARE @qtd_produtos INT		SET @qtd_produtos = (SELECT COUNT(p.codigo) FROM produto p WHERE p.qtd_estoque < @val_min)		RETURN @qtd_produtosENDSELECT dbo.fn_valent(1) AS menor/*Fazer uma Multi Statement Table Function que liste o código, o nome e a
quantidade dos produtos que estão com o estoque abaixo de um valor de
entrada (O valor mínimo deve ser parâmetro de entrada)
*/

CREATE FUNCTION fn_abaixo_valor(@val_min INT)
RETURNS @tabela TABLE (
codigo				INT				NOT NULL,
nome				VARCHAR(30)		NOT NULL,
valor_unitario		DECIMAL(7,2)	NOT NULL,
qtd_estoque			INT				NOT NULL
)
AS
BEGIN
		INSERT INTO @tabela(codigo, nome, valor_unitario, qtd_estoque)
		SELECT codigo, nome, valor_unitario, qtd_estoque
		FROM produto 
		WHERE qtd_estoque < @val_min

		RETURN
END

SELECT * FROM fn_abaixo_valor(1)
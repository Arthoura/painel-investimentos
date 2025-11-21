-- Remove os investimentos inseridos para o cliente de teste
DELETE FROM dbo.investimento WHERE id = 15;

-- Remove o produto inserido para o cenário de teste
DELETE FROM dbo.produto WHERE id = 15;

-- Remove o cliente inserido para o cenário de teste
DELETE FROM dbo.cliente WHERE id = 15;

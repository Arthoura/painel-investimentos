-- Cliente
INSERT INTO dbo.cliente (id, nome, email, volume, frequencia_movimentacoes_mensal, preferencia_liquidez_rentabilidade)
VALUES (15, 'Arthur', 'arthur@email.com', 5000.00, 3, 'LIQUIDEZ');

-- Produto
INSERT INTO dbo.produto (id, nome, tipo, rentabilidade, risco, liquidez)
VALUES (15, 'CDB CAIXA', 'CDB', 0.109, 'baixo', 'alta');

-- Investimento
INSERT INTO dbo.investimento (id, cliente_id, produto_id, valor, data)
VALUES (15, 15, 15, 350.00, '2025-10-30 00:00:00');

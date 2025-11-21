INSERT INTO dbo.produto (id, nome, tipo, rentabilidade, risco, liquidez)
VALUES (111, 'CDB Caixa Liquidez Diária', 'CDB', 0.095, 'baixo', 'alta');

INSERT INTO dbo.simulacao (client_id, produto_id, valor_investido, valor_final, prazo_meses, data_simulacao)
VALUES (15, 111, 10000.00, 10950.00, 12, '2025-11-25T00:00:00');

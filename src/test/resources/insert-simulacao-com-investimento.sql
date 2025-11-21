-- Cliente
INSERT INTO dbo.cliente (
    id,
    nome,
    email,
    volume,
    frequencia_movimentacoes_mensal,
    preferencia_liquidez_rentabilidade
) VALUES (
    1,
    'Ivson',
    'ivson@email.com',
    5000.00,
    3,
    'EQUILIBRIO'
);

-- Produto válido
INSERT INTO dbo.produto (
    id,
    nome,
    tipo,
    rentabilidade,
    risco,
    liquidez
) VALUES (
    1,
    'CDB Banco X',
    'CDB',
    0.12,
    'baixo',
    'alta'
);

-- Investimento do cliente
INSERT INTO dbo.investimento (
    id,
    cliente_id,
    produto_id,
    valor,
    data
) VALUES (
    1,
    1,
    1,
    5000.00,
    '2025-11-20T00:00:00'
);

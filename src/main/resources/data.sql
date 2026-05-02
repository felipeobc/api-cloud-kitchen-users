INSERT INTO tb_users (
    name,
    email,
    login,
    password,
    address,
    active,
    owner,
    created_at,
    updated_at
) VALUES
      (
          'João Silva',
          'joao.silva@email.com',
          'joaosilva',
          '$2a$10$wN36sCef1vclCpoICs5EDeD0AiMOE7EHrCn/aeWEV3q/KCIb0/sjC',
          'Rua das Flores, 100 - São Paulo - SP - CEP: 01001-000',
          TRUE,
          TRUE,
          CURRENT_TIMESTAMP,
          CURRENT_TIMESTAMP
      ),
      (
          'Maria Oliveira',
          'maria.oliveira@email.com',
          'mariaoliveira',
          '$2a$10$7EqJtq98hPqEX7fNZaFWoOhi3n0KDrtwxbrCXnYYf04bFu6SAd5kW',
          'Av. Paulista, 1500 - São Paulo - SP - CEP: 01310-200',
          TRUE,
          FALSE,
          CURRENT_TIMESTAMP,
          CURRENT_TIMESTAMP
      ),
      (
          'Carlos Santos',
          'carlos.santos@email.com',
          'carlossantos',
          '$2a$10$7EqJtq98hPqEX7fNZaFWoOhi3n0KDrtwxbrCXnYYf04bFu6SAd5kW',
          'Rua Augusta, 250 - São Paulo - SP - CEP: 01304-000',
          TRUE,
          FALSE,
          CURRENT_TIMESTAMP,
          CURRENT_TIMESTAMP
      ),
      (
          'Ana Costa',
          'ana.costa@email.com',
          'anacosta',
          '$2a$10$7EqJtq98hPqEX7fNZaFWoOhi3n0KDrtwxbrCXnYYf04bFu6SAd5kW',
          'Rua Vergueiro, 900 - São Paulo - SP - CEP: 01504-001',
          TRUE,
          FALSE,
          CURRENT_TIMESTAMP,
          CURRENT_TIMESTAMP
      ),
      (
          'Pedro Almeida',
          'pedro.almeida@email.com',
          'pedroalmeida',
          '$2a$10$7EqJtq98hPqEX7fNZaFWoOhi3n0KDrtwxbrCXnYYf04bFu6SAd5kW',
          'Av. Brigadeiro Faria Lima, 45 - São Paulo - SP - CEP: 01452-000',
          TRUE,
          FALSE,
          CURRENT_TIMESTAMP,
          CURRENT_TIMESTAMP
      )
    ON CONFLICT (email) DO NOTHING;
# 🎓**Carteirinha Universitária (Julius Edition)**

Este projeto é uma aplicação Android desenvolvida para servir como uma credencial digital universitária. Originalmente concebido para substituir a carteirinha física via QR Code, o app foi adaptado com funcionalidades de benefícios e ofertas, baseado no personagem Julius — focando em economia e "não gastar dinheiro".

---

### 🚀 **Funcionalidades**

:shipit: **Credencial Digital:** Interface para visualização de dados do usuário e status acadêmico.

📱 **QR Code Dinâmico:** Gerador de QR Code que alterna a cada 10 segundos para evitar fraudes e capturas de tela estáticas.

📅 **Cronograma Mensal:** Agenda de 30 dias persistida localmente para organização de tarefas e compromissos.

💰 **Julius Shop:** Seção de ofertas e cashbacks simulada, com links para lojas reais, focada no "desconto máximo".

📸 **Integração Nativa:** Atalhos para acesso à câmera e sistema de notificações via Toast.

👤 **Cadastro de Usuário:** Sistema CRUD completo para gerenciar as informações do perfil do estudante.

---

### 🛠️ **Tecnologias e Arquitetura**
O projeto utiliza as práticas mais modernas recomendadas para o desenvolvimento Android:

**Linguagem:** Kotlin

**UI:** Jetpack Compose (Desenvolvimento declarativo de interface)

**Arquitetura:** MVVM (Model-View-ViewModel) para separação de responsabilidades.

**Banco de Dados:** Room Database com suporte a Coroutines e Flow.

**Reatividade:** StateFlow e LaunchedEffect para gestão de estado e efeitos colaterais.

**Navegação:** Jetpack Navigation Compose.

---

### **📂 Estrutura do Projeto**
O código está organizado seguindo padrões de Clean Architecture:

*uiprojeto/:* Contém os Composables (telas e componentes).

*mvvm/data/:* Camada de lógica de negócio, contendo ViewModels e Repositories.

*data/database/:* Configuração do banco de dados Room (Entities, DAOs e AppDatabase).

---

### 💡 Como Funciona o Banco de Dados?
O app utiliza o padrão Singleton para garantir uma única instância do banco de dados. Ao iniciar a tela de Cronograma, o CronogramaRepository verifica se a tabela está vazia; caso positivo, ele popula automaticamente os 30 dias do mês, garantindo que o usuário sempre tenha uma estrutura pronta para editar.

---
### 📝 *Nota de Desenvolvimento*
*Este projeto foi baseado em uma aplicação real de uso universitário. Atualmente, encontra-se em estado de "concluído para portfólio", servindo como demonstração de habilidades em persistência de dados local, fluxos de navegação complexos e UI moderna com Compose.*

## 👥NOMES DOS ENVOLVIDOS NO PROJETO:
- Heitor Augusto Andrade
- Jhanny Aparecida Rebeiko Pianovski

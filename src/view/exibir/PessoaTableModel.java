package view.exibir;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import bean.Pessoa;

/* package */ class PessoaTableModel extends AbstractTableModel {

   private static final long serialVersionUID = 1L;

   private static final int COL_CODIGO = 0;
   private static final int COL_NOME = 1;
   private static final int COL_TELEFONE = 2;
   private static final int COL_EMAIL = 3;
   private static final int COL_CPF_CNPJ = 4;
   List<Pessoa> linhas;
   private String[] colunas = new String[]{"Código", "Nome", "Telefone", "Email", "CPF/CNPJ"};

   public PessoaTableModel(List<Pessoa> pessoas) {
      this.linhas = new ArrayList<>(pessoas);
   }

   public int getRowCount() {
      return linhas.size();
   }

   public int getColumnCount() {
      return colunas.length;
   }

   public String getColumnName(int columnIndex) {
      return colunas[columnIndex];
   }


  public Class<?> getColumnClass(int columnIndex) {
      if (columnIndex == COL_CODIGO) {
         return Integer.class;
      }
      return String.class;
   }

   public boolean isCellEditable(int rowIndex, int columnIndex) {
      if (columnIndex == COL_CODIGO) {
         return false;
      }
      return true;
   }

   public Object getValueAt(int row, int column) {

      Pessoa m = linhas.get(row);

      if (column == COL_CODIGO) {
         return m.getCodigo();
      } else if (column == COL_NOME) {
         return m.getNome();
      } else if (column == COL_TELEFONE) {
         return m.getTelefone();
      } else if (column == COL_EMAIL) {
         return m.getEmail();
      } else if (column == COL_CPF_CNPJ) {
         return m.getCpfCnpj();
      }
      return "";
   }

   public void setValueAt(Object aValue, int row, int column) {
      Pessoa u = linhas.get(row);
      if (column == COL_CODIGO) {
         u.setCodigo((Integer) aValue);
      } else if (column == COL_NOME) {
         u.setNome(aValue.toString());
      } else if (column == COL_TELEFONE) {
         u.setTelefone(aValue.toString());
      } else if (column == COL_EMAIL) {
         u.setEmail(aValue.toString());
      } else if (column == COL_CPF_CNPJ) {
         u.setCpfCnpj(aValue.toString());
      }
   }

   public Pessoa getPessoa(int row) {
      return linhas.get(row);
   }

   public void addPessoa(Pessoa pessoa) {
      linhas.add(pessoa);
      int ultimoIndice = getRowCount() - 1;
      fireTableRowsInserted(ultimoIndice, ultimoIndice);

   }

   public void updatePessoa(int indiceLinha, Pessoa p) {
      linhas.set(indiceLinha, p);
      fireTableRowsUpdated(indiceLinha, indiceLinha);
   }

   public void removePessoa(int indiceLinha) {
      linhas.remove(indiceLinha);
      fireTableRowsDeleted(indiceLinha, indiceLinha);
   }

}
package com.icma.cdm.example.repo.pairoff;

public class InterestRateCalculationExample extends AbstractExample {


        public PairOffRequestInstruction createPairOffRequest(List<TradeState> tradeStateList) throws IOException {

            IcmaRepoUtil ru = new IcmaRepoUtil();

            CItem initiatingparty = (CItem) this.initiatingFirmLeiField.getSelectedItem();
            String initiatingpartyStr = initiatingparty.getClabel();
            String initiatingpartyLEIStr = initiatingparty.getCValue();
            if (initiatingpartyLEIStr.equals("")) {
                JOptionPane.showMessageDialog(panel1, "Initiator cannot be empty", "Alert", JOptionPane.INFORMATION_MESSAGE);
                throw new IOException("Initiating Party");
            }

            CItem counterparty = (CItem) this.counterpartyField.getSelectedItem();
            String counterpartyStr = counterparty.getClabel();
            String counterpartyLEIStr = counterparty.getCValue();
            if (counterpartyLEIStr.equals("")) {
                JOptionPane.showMessageDialog(panel1, "Counterparty cannot be empty", "Alert", JOptionPane.INFORMATION_MESSAGE);
                throw new IOException("Counterparty");
            }

            Party initiatingParty = ru.createRepoParty(initiatingpartyLEIStr,"LEI",initiatingpartyStr);
            Party cdmcounterparty = ru.createRepoParty(counterpartyLEIStr,"LEI", counterpartyStr);
            String pairId = this.pairoffIdentifierField.getText();


            PairOffRequestInstruction pairOffRequestInstruction = PairOffRequestInstruction.builder()
                    .addTradesState(tradeStateList)
                    .setCounterparty(initiatingParty)
                    .setCounterparty(cdmcounterparty)
                    .setPairoffIdentifier(Identifier.builder()
                            .setAssignedIdentifier(List.of(AssignedIdentifier.builder()
                                    .setIdentifierValue(pairId))))
                    .setInitiatingPartyRole(PartyRole.builder()
                            .setRole(PartyRoleEnum.BUYER))
                    .setCounterpartyRole(PartyRole.builder()
                            .setRole(PartyRoleEnum.SELLER))
                    .setCashCurrency(CurrencyCodeEnum.GBP)
                    .setCreditAccount(Account.builder()
                            .setAccountName(FieldWithMetaString.builder()
                                    .setValue("BuyerAccount")))
                    .setDebitAccount(Account.builder()
                            .setAccountName(FieldWithMetaString.builder()
                                    .setValue("SellerAccount")))
                    .setNetSettlementAmount(MeasureBase.builder()
                            .setValue(BigDecimal.valueOf(19876.00)))
                    .setLegalAgreement(List.of(LegalAgreement.builder()
                                    .setLegalAgreementIdentification(LegalAgreementIdentification.builder()
                                            .setGoverningLaw(GoverningLawEnum.GBEN)
                                            .setPublisher(LegalAgreementPublisherEnum.ICMA)
                                            .setAgreementName(AgreementName.builder()
                                                    .setOtherAgreement("GlobalMasterPairOffAgreement-Demo Purpose Only")))))

                    .build();


        return pairOffRequestInstruction;

        }
}
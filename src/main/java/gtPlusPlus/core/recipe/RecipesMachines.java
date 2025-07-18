package gtPlusPlus.core.recipe;

import static goodgenerator.loader.Loaders.supercriticalFluidTurbineCasing;
import static gregtech.api.enums.Mods.EternalSingularity;
import static gregtech.api.enums.Mods.Railcraft;
import static gregtech.api.enums.Mods.RemoteIO;
import static gregtech.api.recipe.RecipeMaps.assemblerRecipes;
import static gregtech.api.recipe.RecipeMaps.cutterRecipes;
import static gregtech.api.recipe.RecipeMaps.distilleryRecipes;
import static gregtech.api.util.GTModHandler.getModItem;
import static gregtech.api.util.GTRecipeBuilder.INGOTS;
import static gregtech.api.util.GTRecipeBuilder.MINUTES;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;
import static gregtech.api.util.GTRecipeBuilder.STACKS;
import static gregtech.api.util.GTRecipeBuilder.TICKS;
import static gregtech.api.util.GTRecipeConstants.AssemblyLine;
import static gregtech.api.util.GTRecipeConstants.CHEMPLANT_CASING_TIER;
import static gregtech.api.util.GTRecipeConstants.RESEARCH_ITEM;
import static gregtech.api.util.GTRecipeConstants.SCANNING;
import static gtPlusPlus.api.recipe.GTPPRecipeMaps.chemicalPlantRecipes;
import static gtPlusPlus.core.recipe.common.CI.circuits;

import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import com.google.common.collect.ImmutableList;

import bartworks.system.material.WerkstoffLoader;
import goodgenerator.loader.Loaders;
import gregtech.GTMod;
import gregtech.api.GregTechAPI;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Mods;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.enums.ToolDictNames;
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTUtility;
import gregtech.api.util.recipe.Scanning;
import gtPlusPlus.api.objects.Logger;
import gtPlusPlus.core.block.ModBlocks;
import gtPlusPlus.core.item.ModItems;
import gtPlusPlus.core.item.crafting.ItemDummyResearch;
import gtPlusPlus.core.item.crafting.ItemDummyResearch.ASSEMBLY_LINE_RESEARCH;
import gtPlusPlus.core.material.MaterialMisc;
import gtPlusPlus.core.material.MaterialsAlloy;
import gtPlusPlus.core.material.MaterialsElements;
import gtPlusPlus.core.recipe.common.CI;
import gtPlusPlus.core.util.minecraft.ItemUtils;
import gtPlusPlus.core.util.minecraft.RecipeUtils;
import gtPlusPlus.xmod.gregtech.api.enums.GregtechItemList;
import gtPlusPlus.xmod.gregtech.common.covers.CoverManager;
import gtPlusPlus.xmod.gregtech.common.helpers.VolumetricFlaskHelper;
import tectech.recipe.TTRecipeAdder;
import tectech.thing.CustomItemList;
import toxiceverglades.dimension.DimensionEverglades;

public class RecipesMachines {

    // Outputs
    public static ItemStack RECIPE_Buffer_ULV = GregtechItemList.Energy_Buffer_1by1_ULV.get(1);
    public static ItemStack RECIPE_Buffer_LV = GregtechItemList.Energy_Buffer_1by1_LV.get(1);
    public static ItemStack RECIPE_Buffer_MV = GregtechItemList.Energy_Buffer_1by1_MV.get(1);
    public static ItemStack RECIPE_Buffer_HV = GregtechItemList.Energy_Buffer_1by1_HV.get(1);
    public static ItemStack RECIPE_Buffer_EV = GregtechItemList.Energy_Buffer_1by1_EV.get(1);
    public static ItemStack RECIPE_Buffer_IV = GregtechItemList.Energy_Buffer_1by1_IV.get(1);
    public static ItemStack RECIPE_Buffer_LuV = GregtechItemList.Energy_Buffer_1by1_LuV.get(1);
    public static ItemStack RECIPE_Buffer_ZPM = GregtechItemList.Energy_Buffer_1by1_ZPM.get(1);
    public static ItemStack RECIPE_Buffer_UV = GregtechItemList.Energy_Buffer_1by1_UV.get(1);
    public static ItemStack RECIPE_Buffer_MAX = GregtechItemList.Energy_Buffer_1by1_MAX.get(1);
    // Industrial Centrifuge
    public static ItemStack RECIPE_IndustrialCentrifugeController;
    public static ItemStack RECIPE_IndustrialCentrifugeCasing;
    // Industrial Coke Oven
    public static ItemStack RECIPE_IndustrialCokeOvenController;
    public static ItemStack RECIPE_IndustrialCokeOvenFrame;
    public static ItemStack RECIPE_IndustrialCokeOvenCasingA;
    public static ItemStack RECIPE_IndustrialCokeOvenCasingB;
    // Industrial Electrolyzer
    public static ItemStack RECIPE_IndustrialElectrolyzerController;
    public static ItemStack RECIPE_IndustrialElectrolyzerFrame;
    // Industrial Material Press
    public static ItemStack RECIPE_IndustrialMaterialPressController;
    public static ItemStack RECIPE_IndustrialMaterialPressFrame;
    // Industrial Maceration Stack
    public static ItemStack RECIPE_IndustrialMacerationStackController;
    public static ItemStack RECIPE_IndustrialMacerationStackFrame;
    // Industrial Wire Factory
    public static ItemStack RECIPE_IndustrialWireFactoryController;
    public static ItemStack RECIPE_IndustrialWireFactoryFrame;
    // Industrial Multi Tank
    public static ItemStack RECIPE_IndustrialMultiTankController;
    public static ItemStack RECIPE_IndustrialMultiTankFrame;
    // Industrial Matter Fabricator
    public static ItemStack RECIPE_IndustrialMatterFabController;
    public static ItemStack RECIPE_IndustrialMatterFabFrame;
    public static ItemStack RECIPE_IndustrialMatterFabCoil;
    // Industrial Blast Smelter
    public static ItemStack RECIPE_IndustrialBlastSmelterController;
    public static ItemStack RECIPE_IndustrialBlastSmelterFrame;
    public static ItemStack RECIPE_IndustrialBlastSmelterCoil;
    // Industrial Sieve
    public static ItemStack RECIPE_IndustrialSieveController;
    public static ItemStack RECIPE_IndustrialSieveFrame;
    public static ItemStack RECIPE_IndustrialSieveGrate;
    // Industrial Tree Farmer
    public static ItemStack RECIPE_TreeFarmController;
    public static ItemStack RECIPE_TreeFarmFrame;
    // Tesseracts
    public static ItemStack RECIPE_TesseractGenerator;
    public static ItemStack RECIPE_TesseractTerminal;
    // Thermal Boiler
    public static ItemStack RECIPE_ThermalBoilerController;
    public static ItemStack RECIPE_ThermalBoilerCasing;

    // Thorium Reactor
    public static ItemStack RECIPE_LFTRController;
    public static ItemStack RECIPE_LFTROuterCasing;
    public static ItemStack RECIPE_LFTRInnerCasing;

    // Nuclear Salt Processing Plant
    public static ItemStack RECIPE_SaltPlantController;

    // Cyclotron
    public static ItemStack RECIPE_CyclotronController;
    public static ItemStack RECIPE_CyclotronOuterCasing;
    public static ItemStack RECIPE_CyclotronInnerCoil;

    // Wire
    public static String cableTier4 = "cableGt04Gold";
    public static String cableTier6 = "cableGt04Tungsten";

    public static String pipeTier1 = "pipeHuge" + "Clay";
    public static String pipeTier2 = "pipeHuge" + "Potin";
    public static String pipeTier3 = "pipeHuge" + "Steel";
    public static String pipeTier4 = "pipeHuge" + "StainlessSteel";
    public static String pipeTier7 = "pipeHuge" + "Tantalloy60";

    // EV/IV MACHINES
    public static ItemStack IV_MACHINE_Electrolyzer;
    public static ItemStack EV_MACHINE_Centrifuge;
    public static ItemStack EV_MACHINE_BendingMachine;
    public static ItemStack IV_MACHINE_Wiremill;
    public static ItemStack EV_MACHINE_Macerator;
    public static ItemStack IV_MACHINE_Macerator;
    public static ItemStack IV_MACHINE_Cutter;
    public static ItemStack IV_MACHINE_Extruder;
    public static ItemStack HV_MACHINE_Sifter;
    public static ItemStack EV_MACHINE_ThermalCentrifuge;
    public static ItemStack EV_MACHINE_OreWasher;
    public static ItemStack IV_MACHINE_AlloySmelter;
    public static ItemStack IV_MACHINE_Mixer;
    public static ItemStack EV_MACHINE_ChemicalBath;

    // Plates
    public static String plateBronze = "plateBronze";
    public static String plateSteel = "plateSteel";

    // Pipes
    public static String pipeHugeStainlessSteel = "pipeHugeStainlessSteel";

    // Lava Boiler
    public static ItemStack boiler_Coal;
    public static ItemStack IC2MFE;
    public static ItemStack IC2MFSU;

    // Misc
    public static ItemStack INPUT_RCCokeOvenBlock;

    public static void loadRecipes() {
        run();
        Logger.INFO("Loading Recipes for the Various machine blocks.");
    }

    private static void run() {

        initModItems();
        tieredMachineHulls();
        energyCores();
        wirelessChargers();
        largeArcFurnace();
        industrialVacuumFurnace();
        fakeMachineCasingCovers();
        overflowValveCovers();
        superBuses();
        distillus();
        algaeFarm();
        chemPlant();
        zyngen();
        milling();
        sparging();
        chisels();
        rockBreaker();
        thermicFluidHeater();
        advHeatExchanger();
        chiselBuses();
        solidifierHatches();

        gt4FarmManager();
        gt4Inventory();

        multiForgeHammer();
        multiMolecularTransformer();
        multiXlTurbines();
        multiSolarTower();
        multiElementalDuplicator();

        resonanceChambers();
        modulators();
    }

    private static void thermicFluidHeater() {

        RecipeUtils.addShapedGregtechRecipe(
            CI.getPlate(5, 1),
            "circuitElite",
            CI.getPlate(5, 1),
            pipeTier7,
            ItemList.Machine_IV_FluidHeater.get(1),
            pipeTier7,
            CI.getPlate(5, 1),
            "circuitData",
            CI.getPlate(5, 1),
            GregtechItemList.Controller_IndustrialFluidHeater.get(1));
    }

    private static void advHeatExchanger() {
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(18),
                ItemList.Machine_Multi_HeatExchanger.get(1),
                CI.getDoublePlate(6, 8),
                CI.getScrew(6, 16),
                CI.getCircuit(5, 8))
            .itemOutputs(GregtechItemList.XL_HeatExchanger.get(1))
            .fluidInputs(CI.tieredMaterials[5].getMolten(8 * INGOTS))
            .duration(60 * SECONDS)
            .eut(TierEU.RECIPE_LuV)
            .addTo(assemblerRecipes);
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(18),
                ItemList.Casing_StableTitanium.get(1),
                CI.getPlate(5, 4),
                CI.getScrew(5, 8))
            .itemOutputs(GregtechItemList.Casing_XL_HeatExchanger.get(1))
            .fluidInputs(CI.tieredMaterials[5].getMolten(2 * INGOTS))
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_LuV)
            .addTo(assemblerRecipes);

    }

    private static void gt4FarmManager() {

        ItemList[] aInputHatches = new ItemList[] { ItemList.Hatch_Input_LV, ItemList.Hatch_Input_MV,
            ItemList.Hatch_Input_HV, ItemList.Hatch_Input_EV, ItemList.Hatch_Input_IV, ItemList.Hatch_Input_LuV,
            ItemList.Hatch_Input_ZPM, ItemList.Hatch_Input_UV };
        GregtechItemList[] aOutputMachines = new GregtechItemList[] { GregtechItemList.GT4_Crop_Harvester_LV,
            GregtechItemList.GT4_Crop_Harvester_MV, GregtechItemList.GT4_Crop_Harvester_HV,
            GregtechItemList.GT4_Crop_Harvester_EV, GregtechItemList.GT4_Crop_Harvester_IV,
            GregtechItemList.GT4_Crop_Harvester_LuV, GregtechItemList.GT4_Crop_Harvester_ZPM,
            GregtechItemList.GT4_Crop_Harvester_UV };

        int aTier = 1;
        for (int i = 0; i < 8; i++) {
            RecipeUtils.addShapedRecipe(
                CI.getRobotArm(aTier, 1),
                CI.getSensor(aTier, 1),
                CI.getRobotArm(aTier, 1),
                ItemUtils.getOrePrefixStack(OrePrefixes.plate, CI.tieredMaterials[aTier], 1),
                CI.getTieredMachineHull(aTier, 1),
                ItemUtils.getOrePrefixStack(OrePrefixes.plate, CI.tieredMaterials[aTier], 1),
                CI.circuits[aTier],
                aInputHatches[i].get(1),
                CI.circuits[aTier],
                aOutputMachines[i].get(1));
            aTier++;
        }
    }

    private static void gt4Inventory() {

        GregtechItemList[] aOutputElectricCraftingTable = new GregtechItemList[] {
            GregtechItemList.GT4_Electric_Auto_Workbench_LV, GregtechItemList.GT4_Electric_Auto_Workbench_MV,
            GregtechItemList.GT4_Electric_Auto_Workbench_HV, GregtechItemList.GT4_Electric_Auto_Workbench_EV,
            GregtechItemList.GT4_Electric_Auto_Workbench_IV, GregtechItemList.GT4_Electric_Auto_Workbench_LuV,
            GregtechItemList.GT4_Electric_Auto_Workbench_ZPM, GregtechItemList.GT4_Electric_Auto_Workbench_UV };

        int aTier = 1;
        for (int i = 0; i < 8; i++) {
            RecipeUtils.addShapedRecipe(
                ItemUtils.getOrePrefixStack(OrePrefixes.plate, CI.tieredMaterials[aTier], 1),
                new ItemStack(Blocks.crafting_table),
                ItemUtils.getOrePrefixStack(OrePrefixes.plate, CI.tieredMaterials[aTier], 1),
                CI.circuits[aTier],
                CI.getTieredMachineHull(aTier),
                CI.circuits[aTier],
                ItemUtils.getOrePrefixStack(OrePrefixes.plate, CI.tieredMaterials[aTier], 1),
                CI.getRobotArm(aTier, 1),
                ItemUtils.getOrePrefixStack(OrePrefixes.plate, CI.tieredMaterials[aTier], 1),
                aOutputElectricCraftingTable[i].get(1));
            aTier++;
        }
    }

    private static void multiForgeHammer() {

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Hull_IV.get(2),
                ItemList.Machine_IV_Hammer.get(1),
                CI.getPlate(4, 8),
                CI.getBolt(5, 32),
                MaterialsElements.getInstance().ZIRCONIUM.getFineWire(32),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.IV, 4L))
            .itemOutputs(GregtechItemList.Controller_IndustrialForgeHammer.get(1))
            .fluidInputs(CI.getTieredFluid(4, 12 * INGOTS))
            .duration(30 * SECONDS)
            .eut(TierEU.RECIPE_IV)
            .addTo(assemblerRecipes);

        GTModHandler.addCraftingRecipe(
            GregtechItemList.Casing_IndustrialForgeHammer.get(1),
            CI.bitsd,
            new Object[] { "IBI", "HCH", "IHI", 'I', CI.getPlate(4, 1), 'B', MaterialsAlloy.BABBIT_ALLOY.getPlate(1),
                'C', ItemList.Casing_HeatProof.get(1), 'H', MaterialsAlloy.HASTELLOY_X.getRod(1) });
    }

    private static void multiMolecularTransformer() {
        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getTieredGTPPMachineCasing(6, 1),
                CI.getPlate(5, 16),
                CI.getBolt(5, 32),
                MaterialsAlloy.HG1223.getFineWire(64),
                CI.getEmitter(4, 8),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LuV, 10))
            .itemOutputs(
                ItemDummyResearch.getResearchStack(ASSEMBLY_LINE_RESEARCH.RESEARCH_11_MOLECULAR_TRANSFORMER, 1))
            .fluidInputs(CI.getTieredFluid(5, 16 * INGOTS))
            .duration(60 * SECONDS)
            .eut(TierEU.RECIPE_IV)
            .addTo(assemblerRecipes);

        // Molecular Transformer
        GTValues.RA.stdBuilder()
            .metadata(
                RESEARCH_ITEM,
                ItemDummyResearch.getResearchStack(ASSEMBLY_LINE_RESEARCH.RESEARCH_11_MOLECULAR_TRANSFORMER, 1))
            .metadata(SCANNING, new Scanning(50 * SECONDS, TierEU.RECIPE_IV))
            .itemInputs(
                MaterialsAlloy.HG1223.getFineWire(64),
                MaterialsAlloy.HG1223.getFineWire(64),
                ItemList.Electric_Motor_IV.get(16),
                ItemList.Energy_LapotronicOrb.get(16),
                CI.getTieredComponent(OrePrefixes.cableGt12, 6, 16),
                CI.getTieredComponent(OrePrefixes.wireGt16, 5, 32),
                MaterialsAlloy.ZERON_100.getFrameBox(4),
                MaterialsAlloy.ZIRCONIUM_CARBIDE.getPlateDouble(32),
                MaterialsAlloy.BABBIT_ALLOY.getPlate(64),
                MaterialsAlloy.LEAGRISIUM.getGear(8),
                new Object[] { "circuitData", 64 },
                new Object[] { "circuitElite", 32 },
                new Object[] { "circuitMaster", 16 },
                GregtechItemList.Laser_Lens_WoodsGlass.get(1))
            .fluidInputs(
                MaterialsAlloy.NITINOL_60.getFluidStack(18 * INGOTS),
                MaterialsAlloy.INCOLOY_MA956.getFluidStack(1 * STACKS + 8 * INGOTS),
                MaterialsAlloy.KANTHAL.getFluidStack(4 * INGOTS))
            .itemOutputs(GregtechItemList.Controller_MolecularTransformer.get(1))
            .eut(TierEU.RECIPE_LuV)
            .duration(2 * MINUTES)
            .addTo(AssemblyLine);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(16),
                CI.getPlate(6, 4),
                CI.getScrew(6, 8),
                MaterialsElements.getInstance().PALLADIUM.getFineWire(16),
                CI.getSensor(5, 2),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.IV, 4))
            .itemOutputs(GregtechItemList.Casing_Molecular_Transformer_1.get(1))
            .fluidInputs(CI.getTieredFluid(5, 4 * INGOTS))
            .duration(20 * SECONDS)
            .eut(TierEU.RECIPE_IV)
            .addTo(assemblerRecipes);
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(16),
                CI.getPlate(5, 4),
                CI.getScrew(5, 8),
                ItemList.Casing_Coil_Nichrome.get(2),
                CI.getFieldGenerator(3, 2),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.EV, 8))
            .itemOutputs(GregtechItemList.Casing_Molecular_Transformer_2.get(1))
            .fluidInputs(CI.getTieredFluid(5, 4 * INGOTS))
            .duration(20 * SECONDS)
            .eut(TierEU.RECIPE_IV)
            .addTo(assemblerRecipes);
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(16),
                new ItemStack(Blocks.glowstone, 16),
                CI.getGear(5, 8),
                MaterialsElements.getInstance().TITANIUM.getWire04(4),
                CI.getFieldGenerator(4, 2),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.EV, 8))
            .itemOutputs(GregtechItemList.Casing_Molecular_Transformer_3.get(1))
            .fluidInputs(CI.getTieredFluid(5, 4 * INGOTS))
            .duration(60 * SECONDS)
            .eut(TierEU.RECIPE_IV)
            .addTo(assemblerRecipes);

    }

    private static void multiXlTurbines() {

        RecipeUtils.addShapedRecipe(
            CI.getDoublePlate(4, 1),
            CI.getElectricMotor(3, 1),
            CI.getDoublePlate(4, 1),
            ItemUtils.getItemStackOfAmountFromOreDict("cellLubricant", 1),
            ItemList.Casing_Gearbox_Titanium.get(1),
            ItemUtils.getItemStackOfAmountFromOreDict("cellLubricant", 1),
            CI.getDoublePlate(4, 1),
            CI.getElectricMotor(3, 1),
            CI.getDoublePlate(4, 1),
            GregtechItemList.Casing_Turbine_Shaft.get(1));

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(18),
                ItemList.Casing_Turbine.get(1),
                CI.getPlate(4, 4),
                CI.getScrew(4, 8),
                CI.getCircuit(4, 4),
                CI.getGear(3, 8))
            .itemOutputs(GregtechItemList.Hatch_Turbine_Rotor.get(1))
            .fluidInputs(CI.tieredMaterials[3].getMolten(8 * INGOTS))
            .duration(60 * SECONDS)
            .eut(TierEU.RECIPE_EV)
            .addTo(assemblerRecipes);
        // Steam
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(18),
                ItemList.Casing_Turbine.get(1),
                MaterialsAlloy.INCONEL_625.getPlate(4),
                MaterialsAlloy.INCONEL_625.getScrew(8))
            .itemOutputs(GregtechItemList.Casing_Turbine_LP.get(1))
            .fluidInputs(Materials.Aluminium.getMolten(2 * INGOTS))
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(assemblerRecipes);
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(18),
                ItemList.LargeSteamTurbine.get(1),
                CI.getPlate(4, 8),
                CI.getScrew(4, 16),
                CI.getGear(4, 4),
                CI.getCircuit(4, 8))
            .itemOutputs(GregtechItemList.Large_Steam_Turbine.get(1))
            .fluidInputs(CI.tieredMaterials[4].getMolten(8 * INGOTS))
            .duration(60 * SECONDS)
            .eut(TierEU.RECIPE_EV)
            .addTo(assemblerRecipes);
        // Gas
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(18),
                ItemList.Casing_Turbine1.get(1),
                CI.getPlate(5, 4),
                CI.getScrew(5, 8))
            .itemOutputs(GregtechItemList.Casing_Turbine_Gas.get(1))
            .fluidInputs(CI.tieredMaterials[4].getMolten(2 * INGOTS))
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_IV)
            .addTo(assemblerRecipes);
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(18),
                ItemList.LargeGasTurbine.get(1),
                CI.getPlate(6, 8),
                CI.getScrew(6, 16),
                CI.getGear(6, 4),
                CI.getCircuit(6, 8))
            .itemOutputs(GregtechItemList.Large_Gas_Turbine.get(1))
            .fluidInputs(CI.tieredMaterials[6].getMolten(8 * INGOTS))
            .duration(60 * SECONDS)
            .eut(TierEU.RECIPE_LuV)
            .addTo(assemblerRecipes);
        // HP Steam
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(18),
                ItemList.Casing_Turbine2.get(1),
                CI.getPlate(4, 4),
                CI.getScrew(4, 8))
            .itemOutputs(GregtechItemList.Casing_Turbine_HP.get(1))
            .fluidInputs(CI.tieredMaterials[3].getMolten(2 * INGOTS))
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_EV)
            .addTo(assemblerRecipes);
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(18),
                ItemList.LargeHPSteamTurbine.get(1),
                CI.getPlate(5, 8),
                CI.getScrew(5, 16),
                CI.getGear(5, 4),
                CI.getCircuit(5, 8))
            .itemOutputs(GregtechItemList.Large_HPSteam_Turbine.get(1))
            .fluidInputs(CI.tieredMaterials[5].getMolten(8 * INGOTS))
            .duration(60 * SECONDS)
            .eut(TierEU.RECIPE_IV)
            .addTo(assemblerRecipes);
        // Plasma
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(18),
                ItemList.Casing_Turbine3.get(1),
                CI.getPlate(6, 4),
                CI.getScrew(6, 8))
            .itemOutputs(GregtechItemList.Casing_Turbine_Plasma.get(1))
            .fluidInputs(CI.tieredMaterials[5].getMolten(2 * INGOTS))
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_LuV)
            .addTo(assemblerRecipes);
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(18),
                ItemList.LargePlasmaTurbine.get(1),
                CI.getPlate(7, 8),
                CI.getScrew(7, 16),
                CI.getGear(7, 4),
                CI.getCircuit(7, 8))
            .itemOutputs(GregtechItemList.Large_Plasma_Turbine.get(1))
            .fluidInputs(CI.tieredMaterials[7].getMolten(8 * INGOTS))
            .duration(60 * SECONDS)
            .eut(TierEU.RECIPE_ZPM)
            .addTo(assemblerRecipes);
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(18),
                new ItemStack(supercriticalFluidTurbineCasing, 1),
                new ItemStack(WerkstoffLoader.items.get(OrePrefixes.plate), 4, 10101),
                new ItemStack(WerkstoffLoader.items.get(OrePrefixes.screw), 8, 10101))
            .itemOutputs(GregtechItemList.Casing_Turbine_SC.get(1))
            .fluidInputs(FluidRegistry.getFluidStack("molten.adamantium alloy", 2 * INGOTS))
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_LuV)
            .addTo(assemblerRecipes);
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(18),
                GTUtility.copyAmount(1, Loaders.SCTurbine),
                new ItemStack(WerkstoffLoader.items.get(OrePrefixes.plate), 8, 10104),
                new ItemStack(WerkstoffLoader.items.get(OrePrefixes.screw), 16, 10104),
                new ItemStack(WerkstoffLoader.items.get(OrePrefixes.gearGt), 4, 10104),
                CI.getCircuit(7, 8))
            .itemOutputs(GregtechItemList.Large_SCSteam_Turbine.get(1))
            .fluidInputs(FluidRegistry.getFluidStack("molten.hikarium", 8 * INGOTS))
            .duration(60 * SECONDS)
            .eut(TierEU.RECIPE_ZPM)
            .addTo(assemblerRecipes);

    }

    private static void multiSolarTower() {

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(17),
                CI.getTieredGTPPMachineCasing(3, 4),
                MaterialsAlloy.MARAGING250.getPlate(8),
                MaterialsAlloy.MARAGING250.getBolt(8),
                MaterialsAlloy.MARAGING250.getScrew(8),
                CI.getCircuit(5, 8))
            .itemOutputs(GregtechItemList.Industrial_Solar_Tower.get(1))
            .fluidInputs(CI.getTieredFluid(3, 16 * INGOTS))
            .duration(30 * SECONDS)
            .eut(TierEU.RECIPE_EV)
            .addTo(assemblerRecipes);
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(17),
                MaterialsAlloy.MARAGING350.getFrameBox(1),
                MaterialsAlloy.STAINLESS_STEEL.getPlate(4),
                MaterialsAlloy.MARAGING350.getScrew(8))
            .itemOutputs(GregtechItemList.Casing_SolarTower_Structural.get(1))
            .fluidInputs(CI.getTieredFluid(3, 4 * INGOTS))
            .duration(30 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(assemblerRecipes);
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(17),
                MaterialsAlloy.MARAGING250.getFrameBox(1),
                MaterialsAlloy.STAINLESS_STEEL.getPlate(4),
                MaterialsAlloy.MARAGING250.getBolt(16),
                MaterialsElements.getInstance().ALUMINIUM.getScrew(8))
            .itemOutputs(GregtechItemList.Casing_SolarTower_SaltContainment.get(1))
            .fluidInputs(CI.getTieredFluid(3, 4 * INGOTS))
            .duration(30 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(assemblerRecipes);
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(17),
                MaterialsAlloy.MARAGING250.getFrameBox(1),
                MaterialsAlloy.STEEL_BLACK.getPlate(4),
                MaterialsAlloy.MARAGING250.getScrew(8))
            .itemOutputs(GregtechItemList.Casing_SolarTower_HeatContainment.get(1))
            .fluidInputs(CI.getAlternativeTieredFluid(3, 4 * INGOTS))
            .duration(30 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(assemblerRecipes);
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(17),
                CI.getTieredGTPPMachineCasing(2, 1),
                MaterialsAlloy.INCONEL_625.getPlate(2),
                MaterialsAlloy.INCONEL_625.getGear(4),
                CI.getElectricMotor(3, 2),
                CI.getCircuit(3, 4))
            .itemOutputs(GregtechItemList.Solar_Tower_Reflector.get(1))
            .fluidInputs(Materials.Titanium.getMolten(4 * INGOTS))
            .duration(60 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(assemblerRecipes);

    }

    private static void multiElementalDuplicator() {

        // Elemental Duplicator
        GTValues.RA.stdBuilder()
            .metadata(RESEARCH_ITEM, ItemList.Machine_IV_Replicator.get(1))
            .metadata(SCANNING, new Scanning(2 * MINUTES + 30 * SECONDS, TierEU.RECIPE_LuV))
            .itemInputs(
                CI.getTieredMachineHull(7, 4),
                CI.getFieldGenerator(5, 16),
                CI.getElectricMotor(7, 16),
                CI.getElectricPiston(7, 4),
                CI.getEnergyCore(6, 2),
                CI.getPlate(7, 16),
                CI.getScrew(7, 32),
                CI.getBolt(6, 32),
                CI.getTieredComponent(OrePrefixes.rod, 6, 10),
                new Object[] { "circuitUltimate", 20 },
                ItemList.Tool_DataOrb.get(32),
                GregtechItemList.Laser_Lens_Special.get(1))
            .fluidInputs(
                CI.getTieredFluid(7, 32 * INGOTS),
                CI.getAlternativeTieredFluid(6, 16 * INGOTS),
                CI.getTertiaryTieredFluid(6, 16 * INGOTS),
                MaterialsAlloy.BABBIT_ALLOY.getFluidStack(2 * STACKS))
            .itemOutputs(GregtechItemList.Controller_ElementalDuplicator.get(1))
            .eut(TierEU.RECIPE_UV)
            .duration(60 * SECONDS)
            .addTo(AssemblyLine);

        // Data Orb Repository
        GTValues.RA.stdBuilder()
            .metadata(RESEARCH_ITEM, GregtechItemList.Modulator_III.get(1))
            .metadata(SCANNING, new Scanning(1 * MINUTES + 30 * SECONDS, TierEU.RECIPE_LuV))
            .itemInputs(
                CI.getTieredGTPPMachineCasing(7, 2),
                CI.getFieldGenerator(4, 4),
                CI.getEnergyCore(4, 2),
                CI.getPlate(7, 8),
                CI.getScrew(6, 16),
                CI.getBolt(6, 16),
                CI.getTieredComponent(OrePrefixes.rod, 5, 16),
                new Object[] { "circuitMaster", 32 },
                ItemList.Tool_DataOrb.get(32))
            .fluidInputs(
                CI.getTieredFluid(6, 16 * INGOTS),
                CI.getAlternativeTieredFluid(5, 8 * INGOTS),
                CI.getTertiaryTieredFluid(5, 8 * INGOTS),
                MaterialsAlloy.BABBIT_ALLOY.getFluidStack(1 * STACKS))
            .itemOutputs(GregtechItemList.Hatch_Input_Elemental_Duplicator.get(1))
            .eut(TierEU.RECIPE_LuV)
            .duration(60 * SECONDS)
            .addTo(AssemblyLine);

        // Elemental Confinement Shell
        GTValues.RA.stdBuilder()
            .metadata(RESEARCH_ITEM, GregtechItemList.ResonanceChamber_III.get(1))
            .metadata(SCANNING, new Scanning(1 * MINUTES + 30 * SECONDS, TierEU.RECIPE_LuV))
            .itemInputs(
                CI.getTieredMachineHull(6, 5),
                CI.getFieldGenerator(3, 16),
                CI.getEnergyCore(2, 2),
                CI.getPlate(7, 4),
                CI.getScrew(7, 4),
                CI.getBolt(6, 8),
                CI.getTieredComponent(OrePrefixes.rod, 5, 4),
                new Object[] { "circuitElite", 4 },
                ItemList.Tool_DataStick.get(4))
            .fluidInputs(
                CI.getTieredFluid(5, 16 * INGOTS),
                CI.getAlternativeTieredFluid(4, 8 * INGOTS),
                CI.getTertiaryTieredFluid(4, 8 * INGOTS),
                MaterialsAlloy.BABBIT_ALLOY.getFluidStack(16 * INGOTS))
            .itemOutputs(GregtechItemList.Casing_ElementalDuplicator.get(1))
            .eut(TierEU.RECIPE_ZPM)
            .duration(30 * SECONDS)
            .addTo(AssemblyLine);
    }

    private static void resonanceChambers() {
        int aFieldTier = 1;
        int aCasingTier = 4;
        for (int i = 0; i < 4; i++) {
            RecipeUtils.addShapedRecipe(
                CI.getDoublePlate(aCasingTier, 1),
                CI.getFieldGenerator(aFieldTier, 1),
                CI.getDoublePlate(aCasingTier, 1),
                CI.getFieldGenerator(aFieldTier, 1),
                CI.getTieredMachineCasing(aCasingTier),
                CI.getFieldGenerator(aFieldTier, 1),
                CI.getDoublePlate(aCasingTier, 1),
                CI.getFieldGenerator(aFieldTier, 1),
                CI.getDoublePlate(aCasingTier, 1),
                new ItemStack(ModBlocks.blockSpecialMultiCasings2, 1, i));
            aCasingTier++;
            aFieldTier++;
        }
    }

    private static void modulators() {
        int aCasingTier = 4;
        for (int i = 4; i < 8; i++) {
            RecipeUtils.addShapedRecipe(
                circuits[aCasingTier],
                CI.getPlate(aCasingTier, 1),
                circuits[aCasingTier],
                CI.getPlate(aCasingTier, 1),
                CI.getTieredMachineCasing(aCasingTier),
                CI.getPlate(aCasingTier, 1),
                circuits[aCasingTier],
                CI.getPlate(aCasingTier, 1),
                circuits[aCasingTier],
                new ItemStack(ModBlocks.blockSpecialMultiCasings2, 1, i));
            aCasingTier++;
        }
    }

    private static void zyngen() {
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(6),
                CI.getTieredMachineHull(4),
                ItemList.Machine_IV_AlloySmelter.get(1),
                CI.getGear(3, 16),
                CI.getBolt(3, 64),
                CI.getPlate(4, 16))
            .itemOutputs(GregtechItemList.Industrial_AlloySmelter.get(1))
            .fluidInputs(CI.getAlternativeTieredFluid(4, 8 * INGOTS))
            .duration(30 * SECONDS)
            .eut(TierEU.RECIPE_EV)
            .addTo(assemblerRecipes);

    }

    private static void chemPlant() {

        GTModHandler.addCraftingRecipe(
            GregtechItemList.Casing_Machine_Custom_1.get(2L),
            CI.bits,
            new Object[] { "PhP", "PFP", "PwP", 'P', OrePrefixes.plate.get(Materials.Bronze), 'F',
                OrePrefixes.frameGt.get(Materials.Bronze) });

        GTModHandler.addCraftingRecipe(
            GregtechItemList.Casing_Machine_Custom_2.get(2L),
            CI.bits,
            new Object[] { "PPP", "hFw", "PPP", 'P', OrePrefixes.plate.get(Materials.Aluminium), 'F',
                OrePrefixes.frameGt.get(Materials.Aluminium) });

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(19),
                CI.getTieredGTPPMachineCasing(2, 4),
                CI.getTieredComponentOfMaterial(Materials.Aluminium, OrePrefixes.gearGt, 4),
                CI.getTieredComponentOfMaterial(Materials.AnnealedCopper, OrePrefixes.plate, 16),
                CI.getTieredComponentOfMaterial(Materials.Plastic, OrePrefixes.pipeLarge, 4),
                CI.getTieredComponent(OrePrefixes.frameGt, 2, 4))
            .itemOutputs(GregtechItemList.ChemicalPlant_Controller.get(1))
            .fluidInputs(MaterialsAlloy.STEEL_BLACK.getFluidStack(8 * INGOTS))
            .duration(2 * MINUTES)
            .eut(TierEU.RECIPE_MV)
            .addTo(assemblerRecipes);
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(15),
                CI.getTieredGTPPMachineCasing(1, 2),
                ItemList.Hatch_Input_Bus_MV.get(1),
                CI.getTieredComponentOfMaterial(Materials.Bronze, OrePrefixes.gearGt, 8),
                CI.getTieredComponentOfMaterial(Materials.Lead, OrePrefixes.plate, 48),
                CI.getTieredComponentOfMaterial(Materials.SolderingAlloy, OrePrefixes.wireFine, 16))
            .itemOutputs(GregtechItemList.Bus_Catalysts.get(1))
            .fluidInputs(MaterialsAlloy.BRONZE.getFluidStack(8 * INGOTS))
            .duration(60 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(assemblerRecipes);

    }

    private static void algaeFarm() {

        // Give the bad algae a use.
        GTValues.RA.stdBuilder()
            .itemInputs(GregtechItemList.AlgaeBiomass.get(32))
            .itemOutputs(GregtechItemList.GreenAlgaeBiomass.get(4))
            .duration(15 * SECONDS)
            .eut(16)
            .addTo(distilleryRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(21),
                CI.getTieredGTPPMachineCasing(0, 4),
                CI.getTieredComponentOfMaterial(Materials.Aluminium, OrePrefixes.rod, 12),
                CI.getTieredComponentOfMaterial(Materials.Wood, OrePrefixes.plate, 32),
                CI.getTieredComponentOfMaterial(Materials.Steel, OrePrefixes.bolt, 16),
                CI.getTieredComponentOfMaterial(Materials.Redstone, OrePrefixes.dust, 32))
            .itemOutputs(GregtechItemList.AlgaeFarm_Controller.get(1))
            .fluidInputs(MaterialsAlloy.POTIN.getFluidStack(8 * INGOTS))
            .duration(60 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(assemblerRecipes);

    }

    private static void distillus() {
        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Distillation_Tower.get(2),
                GregtechItemList.GTPP_Casing_IV.get(16),
                CI.getTieredComponent(OrePrefixes.circuit, 6, 8))
            .itemOutputs(GregtechItemList.Machine_Adv_DistillationTower.get(1))
            .fluidInputs(
                MaterialsAlloy.AQUATIC_STEEL.getFluidStack(32 * INGOTS),
                MaterialsAlloy.BABBIT_ALLOY.getFluidStack(16 * INGOTS),
                MaterialsAlloy.BRONZE.getFluidStack(1 * STACKS),
                MaterialsAlloy.KANTHAL.getFluidStack(16 * INGOTS))
            .duration(10 * MINUTES)
            .eut(TierEU.RECIPE_LuV)
            .metadata(CHEMPLANT_CASING_TIER, 5)
            .addTo(chemicalPlantRecipes);
    }

    private static void overflowValveCovers() {
        ItemStack[] aOutputs = new ItemStack[] { GregtechItemList.Cover_Overflow_Valve_LV.get(1L),
            GregtechItemList.Cover_Overflow_Valve_MV.get(1L), GregtechItemList.Cover_Overflow_Valve_HV.get(1L),
            GregtechItemList.Cover_Overflow_Valve_EV.get(1L), GregtechItemList.Cover_Overflow_Valve_IV.get(1L), };
        long[] voltageTiers = new long[] { TierEU.RECIPE_LV, TierEU.RECIPE_MV, TierEU.RECIPE_HV, TierEU.RECIPE_EV,
            TierEU.RECIPE_IV };

        for (int tier = 1; tier < aOutputs.length + 1; tier++) {
            GTValues.RA.stdBuilder()
                .itemInputs(
                    GTUtility.getIntegratedCircuit(19),
                    CI.getElectricPump(tier, 2),
                    CI.getElectricMotor(tier, 2),
                    CI.getPlate(tier, 4))
                .itemOutputs(aOutputs[tier - 1].copy())
                .fluidInputs(Materials.SolderingAlloy.getMolten(tier * INGOTS))
                .duration(20 * SECONDS)
                .eut(voltageTiers[tier - 1])
                .addTo(assemblerRecipes);

        }
    }

    private static void tieredMachineHulls() {

        GregtechItemList[] aHulls = new GregtechItemList[] { GregtechItemList.GTPP_Casing_ULV,
            GregtechItemList.GTPP_Casing_LV, GregtechItemList.GTPP_Casing_MV, GregtechItemList.GTPP_Casing_HV,
            GregtechItemList.GTPP_Casing_EV, GregtechItemList.GTPP_Casing_IV, GregtechItemList.GTPP_Casing_LuV,
            GregtechItemList.GTPP_Casing_ZPM, GregtechItemList.GTPP_Casing_UV, GregtechItemList.GTPP_Casing_UHV };

        long[] voltageTiers = new long[] { 16, TierEU.RECIPE_LV, TierEU.RECIPE_MV, TierEU.RECIPE_HV, TierEU.RECIPE_EV,
            TierEU.RECIPE_IV, TierEU.RECIPE_LuV, TierEU.RECIPE_ZPM, TierEU.RECIPE_UV, TierEU.RECIPE_UHV };
        for (int i = 0; i < 10; i++) {
            GTValues.RA.stdBuilder()
                .itemInputs(
                    GTUtility.getIntegratedCircuit(20),
                    CI.getTieredMachineCasing(i),
                    CI.getPlate(i, 8),
                    CI.getGear(i, 2),
                    CI.getTieredComponent(OrePrefixes.cableGt02, i, 4),
                    CI.getTieredComponent(OrePrefixes.circuit, i, 2))
                .itemOutputs(aHulls[i].get(1))
                .fluidInputs(CI.getAlternativeTieredFluid(i, 2 * (i + 1) * INGOTS))
                .duration(20 * SECONDS)
                .eut(voltageTiers[i])
                .addTo(assemblerRecipes);

        }
    }

    private static void initModItems() {
        IC2MFE = getModItem(Mods.IndustrialCraft2.ID, "blockElectric", 1, 1);
        IC2MFSU = getModItem(Mods.IndustrialCraft2.ID, "blockElectric", 1, 2);

        // Lava Boiler
        boiler_Coal = ItemList.Machine_Bronze_Boiler.get(1);

        // IV/EV/HV MACHINES
        IV_MACHINE_Electrolyzer = ItemList.Machine_IV_Electrolyzer.get(1);
        EV_MACHINE_Centrifuge = ItemList.Machine_EV_Centrifuge.get(1);
        EV_MACHINE_BendingMachine = ItemList.Machine_EV_Bender.get(1);
        IV_MACHINE_Wiremill = ItemList.Machine_IV_Wiremill.get(1);
        EV_MACHINE_Macerator = ItemList.Machine_EV_Macerator.get(1);
        IV_MACHINE_Macerator = ItemList.Machine_IV_Macerator.get(1);
        IV_MACHINE_Cutter = ItemList.Machine_IV_Cutter.get(1);
        IV_MACHINE_Extruder = ItemList.Machine_IV_Extruder.get(1);
        HV_MACHINE_Sifter = ItemList.Machine_HV_Sifter.get(1);
        EV_MACHINE_ThermalCentrifuge = ItemList.Machine_EV_ThermalCentrifuge.get(1);
        EV_MACHINE_OreWasher = ItemList.Machine_EV_OreWasher.get(1);
        IV_MACHINE_AlloySmelter = ItemList.Machine_IV_AlloySmelter.get(1);
        IV_MACHINE_Mixer = ItemList.Machine_IV_Mixer.get(1);
        EV_MACHINE_ChemicalBath = ItemList.Machine_EV_ChemicalBath.get(1);
        if (Railcraft.isModLoaded()) {
            // Misc
            INPUT_RCCokeOvenBlock = getModItem(Railcraft.ID, "machine.alpha", 1, 7);
        }
        runModRecipes();
    }

    private static void runModRecipes() {
        // Computer Cube
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(4, CI.getDataOrb()),
                ItemList.Cover_Screen.get(4),
                ItemList.Hull_IV.get(1),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.ZPM, 2))
            .itemOutputs(GregtechItemList.Gregtech_Computer_Cube.get(1))
            .fluidInputs(MaterialsElements.getInstance().TANTALUM.getFluidStack(16 * INGOTS))
            .duration(3 * MINUTES)
            .eut(TierEU.RECIPE_IV)
            .addTo(assemblerRecipes);
        // Circuit programmer
        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Robot_Arm_LV.get(4),
                ItemList.Cover_Controller.get(1),
                ItemList.Hull_MV.get(1),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LV, 2),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.MV, 2))
            .itemOutputs(new ItemStack(ModBlocks.blockCircuitProgrammer))
            .fluidInputs(MaterialsElements.getInstance().IRON.getFluidStack(4 * INGOTS))
            .duration(30 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(assemblerRecipes);

        // Lead Lined Chest
        for (ItemStack plateRubber : OreDictionary.getOres("plateAnyRubber")) {
            GTValues.RA.stdBuilder()
                .itemInputs(
                    ItemList.Hull_LV.get(1),
                    GTUtility.copyAmount(32, plateRubber),
                    ItemUtils.getItemStackOfAmountFromOreDict("plateDenseLead", 9),
                    new ItemStack(Blocks.chest))
                .itemOutputs(new ItemStack(ModBlocks.blockDecayablesChest))
                .fluidInputs(MaterialsElements.getInstance().LEAD.getFluidStack(16 * INGOTS))
                .duration(1 * MINUTES + 30 * SECONDS)
                .eut(60)
                .addTo(assemblerRecipes);

        }

        // RTG
        GTValues.RA.stdBuilder()
            .itemInputs(
                getModItem(Mods.IndustrialCraft2.ID, "blockGenerator", 1, 6),
                MaterialsAlloy.NITINOL_60.getPlate(8),
                MaterialsAlloy.MARAGING350.getGear(4),
                ItemList.Field_Generator_EV.get(8),
                GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Platinum, 32),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LuV, 4))
            .itemOutputs(GregtechItemList.RTG.get(1))
            .fluidInputs(MaterialsAlloy.NIOBIUM_CARBIDE.getFluidStack(16 * INGOTS))
            .duration(10 * MINUTES)
            .eut(TierEU.RECIPE_IV)
            .addTo(assemblerRecipes);
        // Super Jukebox
        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Hull_LV.get(1),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LV, 4),
                ItemUtils.getItemStackOfAmountFromOreDict("plateTumbaga", 8),
                new ItemStack(Blocks.jukebox))
            .itemOutputs(new ItemStack(ModBlocks.blockCustomJukebox))
            .fluidInputs(MaterialsElements.getInstance().COPPER.getFluidStack(2 * INGOTS))
            .duration(30 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(assemblerRecipes);
        // Poo Collector
        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Hull_MV.get(1),
                ItemList.FluidRegulator_MV.get(2),
                CI.getTieredComponent(OrePrefixes.pipeMedium, 2, 2),
                MaterialsAlloy.EGLIN_STEEL.getPlate(4),
                MaterialsAlloy.POTIN.getScrew(6))
            .itemOutputs(new ItemStack(ModBlocks.blockPooCollector))
            .fluidInputs(MaterialsAlloy.TUMBAGA.getFluidStack(4 * INGOTS))
            .duration(60 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(assemblerRecipes);
        // Adv. Poo Collector
        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getTieredMachineHull(-1),
                new ItemStack(ModBlocks.blockPooCollector),
                ItemList.FluidRegulator_IV.get(2),
                CI.getTieredComponent(OrePrefixes.pipeHuge, 6, 4),
                CI.getTieredComponent(OrePrefixes.screw, 6, 16))
            .itemOutputs(new ItemStack(ModBlocks.blockPooCollector, 1, 8))
            .fluidInputs(CI.getAlternativeTieredFluid(5, 9 * INGOTS))
            .duration(5 * MINUTES)
            .eut(TierEU.RECIPE_HV)
            .addTo(assemblerRecipes);

        ItemStack aBronzeBricks = new ItemStack(GregTechAPI.sBlockCasings1, 1, 10);
        // Steam Macerator Multi
        RecipeUtils.addShapedGregtechRecipe(
            aBronzeBricks,
            "gemDiamond",
            aBronzeBricks,
            "craftingPiston",
            MaterialsAlloy.TUMBAGA.getFrameBox(1),
            "craftingPiston",
            aBronzeBricks,
            "gemDiamond",
            aBronzeBricks,
            GregtechItemList.Controller_SteamMaceratorMulti.get(1));

        // Steam Washer Multi
        RecipeUtils.addShapedGregtechRecipe(
            aBronzeBricks,
            "plateWroughtIron",
            aBronzeBricks,
            "rotorTin",
            MaterialsAlloy.TUMBAGA.getFrameBox(1),
            "rotorTin",
            aBronzeBricks,
            "plateWroughtIron",
            aBronzeBricks,
            GregtechItemList.Controller_SteamWasherMulti.get(1));

        // Steam Mixer Multi
        RecipeUtils.addShapedGregtechRecipe(
            aBronzeBricks,
            MaterialsAlloy.TUMBAGA.getRing(1),
            aBronzeBricks,
            MaterialsAlloy.TUMBAGA.getRotor(1),
            MaterialsAlloy.TUMBAGA.getFrameBox(1),
            MaterialsAlloy.TUMBAGA.getRotor(1),
            aBronzeBricks,
            MaterialsAlloy.TUMBAGA.getRing(1),
            aBronzeBricks,
            GregtechItemList.Controller_SteamMixerMulti.get(1));

        ItemStack aWoodenCasing = new ItemStack(GregTechAPI.sBlockCasings9, 1, 2);
        // WaterPump
        RecipeUtils.addShapedGregtechRecipe(
            "frameGtBronze",
            "frameGtBronze",
            "frameGtBronze",
            "frameGtBronze",
            "gearBronze",
            "frameGtBronze",
            aWoodenCasing,
            aWoodenCasing,
            aWoodenCasing,
            GregtechItemList.WaterPump.get(1));

        // Steam Centrifuge Multi
        RecipeUtils.addShapedGregtechRecipe(
            aBronzeBricks,
            "plateWroughtIron",
            aBronzeBricks,
            "gearBronze",
            MaterialsAlloy.TUMBAGA.getFrameBox(1),
            "gearBronze",
            aBronzeBricks,
            "plateWroughtIron",
            aBronzeBricks,
            GregtechItemList.Controller_SteamCentrifugeMulti.get(1));

        // Steam Forge Hammer Multi
        RecipeUtils.addShapedGregtechRecipe(
            aBronzeBricks,
            "plateWroughtIron",
            aBronzeBricks,
            "plateWroughtIron",
            Blocks.anvil,
            "plateWroughtIron",
            aBronzeBricks,
            MaterialsAlloy.TUMBAGA.getFrameBox(1),
            aBronzeBricks,
            GregtechItemList.Controller_SteamForgeHammerMulti.get(1));

        // Steam Compressor Multi
        RecipeUtils.addShapedGregtechRecipe(
            aBronzeBricks,
            "craftingPiston",
            aBronzeBricks,
            MaterialsAlloy.TUMBAGA.getGear(1),
            MaterialsAlloy.TUMBAGA.getFrameBox(1),
            MaterialsAlloy.TUMBAGA.getGear(1),
            aBronzeBricks,
            "craftingPiston",
            aBronzeBricks,
            GregtechItemList.Controller_SteamCompressorMulti.get(1));

        // Steam Hatch
        RecipeUtils.addShapedGregtechRecipe(
            "plateBronze",
            "pipeMediumBronze",
            "plateBronze",
            "plateBronze",
            GregtechItemList.GTFluidTank_ULV.get(1),
            "plateBronze",
            "plateBronze",
            "pipeMediumBronze",
            "plateBronze",
            GregtechItemList.Hatch_Input_Steam.get(1));

        // Steam Input Bus
        RecipeUtils.addShapedGregtechRecipe(
            "plateBronze",
            MaterialsAlloy.TUMBAGA.getPlate(1),
            "plateBronze",
            "plateTin",
            new ItemStack(Blocks.hopper),
            "plateTin",
            "plateBronze",
            MaterialsAlloy.TUMBAGA.getPlate(1),
            "plateBronze",
            GregtechItemList.Hatch_Input_Bus_Steam.get(1));

        // Steam Output Bus
        RecipeUtils.addShapedGregtechRecipe(
            "plateBronze",
            "plateTin",
            "plateBronze",
            MaterialsAlloy.TUMBAGA.getPlate(1),
            new ItemStack(Blocks.hopper),
            MaterialsAlloy.TUMBAGA.getPlate(1),
            "plateBronze",
            "plateTin",
            "plateBronze",
            GregtechItemList.Hatch_Output_Bus_Steam.get(1));

        // Flask Configurator
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(17),
                CI.getTieredMachineHull(2),
                new ItemStack(ModBlocks.blockCircuitProgrammer),
                VolumetricFlaskHelper.getVolumetricFlask(8),
                CI.getTieredComponent(OrePrefixes.pipeSmall, 2, 2),
                CI.getPlate(2, 4))
            .itemOutputs(new ItemStack(ModBlocks.blockVolumetricFlaskSetter, 1))
            .fluidInputs(CI.getAlternativeTieredFluid(1, 8 * INGOTS))
            .duration(60 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(assemblerRecipes);

        // Industrial Centrifuge
        RECIPE_IndustrialCentrifugeController = GregtechItemList.Industrial_Centrifuge.get(1);
        RECIPE_IndustrialCentrifugeCasing = GregtechItemList.Casing_Centrifuge1.get(1);

        // Industrial Centrifuge
        RecipeUtils.addShapedGregtechRecipe(
            "circuitData",
            pipeHugeStainlessSteel,
            "circuitData",
            CI.component_Plate[6],
            EV_MACHINE_Centrifuge,
            CI.component_Plate[6],
            CI.component_Plate[8],
            ItemList.Casing_EV.get(1),
            CI.component_Plate[8],
            RECIPE_IndustrialCentrifugeController);
        // Centrifuge Casing
        RecipeUtils.addShapedGregtechRecipe(
            CI.component_Plate[6],
            "stickTumbaga",
            CI.component_Plate[6],
            CI.component_Plate[8],
            "stickTumbaga",
            CI.component_Plate[8],
            CI.component_Plate[6],
            "stickTumbaga",
            CI.component_Plate[6],
            RECIPE_IndustrialCentrifugeCasing);

        GTValues.RA.stdBuilder()
            .itemInputs(
                MaterialsAlloy.MARAGING250.getPlate(4),
                MaterialsAlloy.INCONEL_792.getPlate(2),
                MaterialsAlloy.TUMBAGA.getRod(3),
                GTUtility.getIntegratedCircuit(1))
            .itemOutputs(RECIPE_IndustrialCentrifugeCasing)
            .duration(2 * SECONDS + 10 * TICKS)
            .eut(16)
            .addTo(assemblerRecipes);

        // Industrial Coke Oven
        RECIPE_IndustrialCokeOvenController = GregtechItemList.Industrial_CokeOven.get(1);
        RECIPE_IndustrialCokeOvenFrame = GregtechItemList.Casing_CokeOven.get(1);
        RECIPE_IndustrialCokeOvenCasingA = GregtechItemList.Casing_CokeOven_Coil1.get(1);
        RECIPE_IndustrialCokeOvenCasingB = GregtechItemList.Casing_CokeOven_Coil2.get(1);

        if (Railcraft.isModLoaded()) {
            // Industrial Coke Oven
            RecipeUtils.addShapedGregtechRecipe(
                CI.component_Plate[7],
                "circuitData",
                CI.component_Plate[7],
                ItemList.Casing_EV.get(1),
                INPUT_RCCokeOvenBlock,
                ItemList.Casing_EV.get(1),
                CI.component_Plate[7],
                "circuitData",
                CI.component_Plate[7],
                RECIPE_IndustrialCokeOvenController);
        }
        // Coke Oven Frame Casing
        RecipeUtils.addShapedGregtechRecipe(
            CI.component_Plate[7],
            CI.component_Rod[7],
            CI.component_Plate[7],
            CI.component_Rod[7],
            "frameGtTantalloy61",
            CI.component_Rod[7],
            CI.component_Plate[7],
            CI.component_Rod[7],
            CI.component_Plate[7],
            RECIPE_IndustrialCokeOvenFrame);
        GTValues.RA.stdBuilder()
            .itemInputs(
                MaterialsAlloy.TANTALLOY_61.getPlate(4),
                MaterialsAlloy.TANTALLOY_61.getRod(4),
                MaterialsAlloy.TANTALLOY_61.getFrameBox(1),
                GTUtility.getIntegratedCircuit(1))
            .itemOutputs(RECIPE_IndustrialCokeOvenFrame)
            .duration(2 * SECONDS + 10 * TICKS)
            .eut(16)
            .addTo(assemblerRecipes);

        // Sturdy Aluminium Machine Casing
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 6),
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Aluminium, 1),
                GTUtility.getIntegratedCircuit(2))
            .itemOutputs(GregtechItemList.Casing_Machine_Custom_2.get(2))
            .duration(2 * SECONDS + 10 * TICKS)
            .eut(16)
            .addTo(assemblerRecipes);

        // Coke Oven Coil 1
        RecipeUtils.addShapedGregtechRecipe(
            plateBronze,
            plateBronze,
            plateBronze,
            "frameGtBronze",
            CI.gearboxCasing_Tier_1,
            "frameGtBronze",
            plateBronze,
            plateBronze,
            plateBronze,
            RECIPE_IndustrialCokeOvenCasingA);
        // Coke Oven Coil 2
        RecipeUtils.addShapedGregtechRecipe(
            plateSteel,
            plateSteel,
            plateSteel,
            "frameGtSteel",
            CI.gearboxCasing_Tier_2,
            "frameGtSteel",
            plateSteel,
            plateSteel,
            plateSteel,
            RECIPE_IndustrialCokeOvenCasingB);

        // Industrial Electrolyzer
        RECIPE_IndustrialElectrolyzerController = GregtechItemList.Industrial_Electrolyzer.get(1);
        RECIPE_IndustrialElectrolyzerFrame = GregtechItemList.Casing_Electrolyzer.get(1);

        // Electrolyzer Frame Casing
        RecipeUtils.addShapedGregtechRecipe(
            "platePotin",
            "stickLongChrome",
            "platePotin",
            "stickLongPotin",
            "frameGtPotin",
            "stickLongPotin",
            "platePotin",
            "stickLongPotin",
            "platePotin",
            RECIPE_IndustrialElectrolyzerFrame);
        GTValues.RA.stdBuilder()
            .itemInputs(
                MaterialsAlloy.POTIN.getPlate(4),
                MaterialsAlloy.POTIN.getLongRod(3),
                GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.Chrome, 1),
                MaterialsAlloy.POTIN.getFrameBox(1),
                GTUtility.getIntegratedCircuit(1))
            .itemOutputs(RECIPE_IndustrialElectrolyzerFrame)
            .duration(2 * SECONDS + 10 * TICKS)
            .eut(16)
            .addTo(assemblerRecipes);

        // Industrial Electrolyzer
        RecipeUtils.addShapedGregtechRecipe(
            "plateStellite",
            "circuitElite",
            "plateStellite",
            ItemList.Casing_IV.get(1),
            IV_MACHINE_Electrolyzer,
            ItemList.Casing_IV.get(1),
            "plateStellite",
            "rotorStellite",
            "plateStellite",
            RECIPE_IndustrialElectrolyzerController);

        // Industrial Material Press
        RECIPE_IndustrialMaterialPressController = GregtechItemList.Industrial_PlatePress.get(1);
        RECIPE_IndustrialMaterialPressFrame = GregtechItemList.Casing_MaterialPress.get(1);

        // Material Press Frame Casing
        RecipeUtils.addShapedGregtechRecipe(
            "plateTitanium",
            "stickLongTumbaga",
            "plateTitanium",
            "stickTantalloy60",
            "frameGtTumbaga",
            "stickTantalloy60",
            "plateTitanium",
            "stickLongTumbaga",
            "plateTitanium",
            RECIPE_IndustrialMaterialPressFrame);
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 4),
                MaterialsAlloy.TANTALLOY_60.getRod(2),
                MaterialsAlloy.TUMBAGA.getLongRod(2),
                MaterialsAlloy.TUMBAGA.getFrameBox(1),
                GTUtility.getIntegratedCircuit(1))
            .itemOutputs(RECIPE_IndustrialMaterialPressFrame)
            .duration(2 * SECONDS + 10 * TICKS)
            .eut(16)
            .addTo(assemblerRecipes);

        // Industrial Material Press
        RecipeUtils.addShapedGregtechRecipe(
            "plateTitanium",
            "circuitData",
            "plateTitanium",
            ItemList.Casing_EV.get(1),
            EV_MACHINE_BendingMachine,
            ItemList.Casing_EV.get(1),
            "plateTitanium",
            "circuitData",
            "plateTitanium",
            RECIPE_IndustrialMaterialPressController);

        // Industrial Maceration Stack
        RECIPE_IndustrialMacerationStackController = GregtechItemList.Industrial_MacerationStack.get(1);
        RECIPE_IndustrialMacerationStackFrame = GregtechItemList.Casing_MacerationStack.get(1);

        // Maceration Frame Casing
        RecipeUtils.addShapedGregtechRecipe(
            "platePalladium",
            "platePalladium",
            "platePalladium",
            "stickPlatinum",
            "frameGtInconel625",
            "stickPlatinum",
            "platePalladium",
            "stickLongPalladium",
            "platePalladium",
            RECIPE_IndustrialMacerationStackFrame);
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.Palladium, 5),
                GTOreDictUnificator.get(OrePrefixes.stick, Materials.Platinum, 2),
                GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.Palladium, 1),
                MaterialsAlloy.INCONEL_625.getFrameBox(1),
                GTUtility.getIntegratedCircuit(1))
            .itemOutputs(RECIPE_IndustrialMacerationStackFrame)
            .duration(2 * SECONDS + 10 * TICKS)
            .eut(16)
            .addTo(assemblerRecipes);

        // Industrial Maceration stack
        RecipeUtils.addShapedGregtechRecipe(
            "plateTitanium",
            EV_MACHINE_Macerator,
            "plateTitanium",
            EV_MACHINE_Macerator,
            "circuitData",
            EV_MACHINE_Macerator,
            "plateTitanium",
            EV_MACHINE_Macerator,
            "plateTitanium",
            RECIPE_IndustrialMacerationStackController);
        // T2-Upgrade Card
        RecipeUtils.addShapedGregtechRecipe(
            "plateTungstenCarbide",
            IV_MACHINE_Macerator,
            "plateTungstenCarbide",
            IV_MACHINE_Macerator,
            "circuitUltimate",
            IV_MACHINE_Macerator,
            "plateTungstenCarbide",
            IV_MACHINE_Macerator,
            "plateTungstenCarbide",
            GregtechItemList.Maceration_Upgrade_Chip.get(1));

        // Industrial Wire Factory
        RECIPE_IndustrialWireFactoryController = GregtechItemList.Industrial_WireFactory.get(1);
        RECIPE_IndustrialWireFactoryFrame = GregtechItemList.Casing_WireFactory.get(1);

        // Wire Factory Frame Casing
        RecipeUtils.addShapedGregtechRecipe(
            "plateBlueSteel",
            "stickBlueSteel",
            "plateBlueSteel",
            "stickBlueSteel",
            "frameGtBlueSteel",
            "stickBlueSteel",
            "plateBlueSteel",
            "stickBlueSteel",
            "plateBlueSteel",
            RECIPE_IndustrialWireFactoryFrame);
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.BlueSteel, 4),
                GTOreDictUnificator.get(OrePrefixes.stick, Materials.BlueSteel, 4),
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.BlueSteel, 1),
                GTUtility.getIntegratedCircuit(1))
            .itemOutputs(RECIPE_IndustrialWireFactoryFrame)
            .duration(2 * SECONDS + 10 * TICKS)
            .eut(16)
            .addTo(assemblerRecipes);

        // Industrial Wire Factory
        RecipeUtils.addShapedGregtechRecipe(
            "plateBlueSteel",
            ItemList.Casing_IV.get(1L),
            "plateBlueSteel",
            "circuitElite",
            ItemList.Machine_IV_Wiremill.get(1L),
            "circuitElite",
            "plateBlueSteel",
            ItemList.Casing_IV.get(1L),
            "plateBlueSteel",
            RECIPE_IndustrialWireFactoryController);

        // Tiered Tanks
        CI.component_Plate[1] = "plateTin";
        pipeTier1 = "pipeLargeClay";
        CI.component_Plate[2] = "plateCopper";
        pipeTier2 = "pipeHugeClay";
        CI.component_Plate[3] = "plateBronze";
        pipeTier3 = "pipeMediumBronze";
        CI.component_Plate[4] = "plateIron";
        pipeTier4 = "pipeMediumSteel";
        CI.component_Plate[5] = "plateSteel";
        CI.component_Plate[6] = "plateRedstone";
        CI.component_Plate[7] = "plateAluminium";
        CI.component_Plate[8] = "plateDarkSteel";
        ItemStack waterBucket = new ItemStack(Items.water_bucket);

        // Allows clearing stored fluids.
        GregtechItemList[] aTanks = new GregtechItemList[] { GregtechItemList.GTFluidTank_ULV,
            GregtechItemList.GTFluidTank_LV, GregtechItemList.GTFluidTank_MV, GregtechItemList.GTFluidTank_HV };
        for (GregtechItemList aTank : aTanks) {
            RecipeUtils.addShapelessGregtechRecipe(new Object[] { aTank.get(1) }, aTank.get(1));
        }

        RecipeUtils.addShapedGregtechRecipe(
            CI.component_Plate[1],
            CI.component_Plate[5],
            CI.component_Plate[1],
            CI.component_Plate[4],
            pipeTier1,
            CI.component_Plate[4],
            CI.component_Plate[4],
            waterBucket,
            CI.component_Plate[4],
            GregtechItemList.GTFluidTank_ULV.get(1));
        RecipeUtils.addShapedGregtechRecipe(
            CI.component_Plate[5],
            CI.component_Plate[4],
            CI.component_Plate[5],
            CI.component_Plate[3],
            pipeTier2,
            CI.component_Plate[3],
            CI.component_Plate[3],
            ItemList.Electric_Pump_LV.get(1),
            CI.component_Plate[3],
            GregtechItemList.GTFluidTank_LV.get(1));
        RecipeUtils.addShapedGregtechRecipe(
            CI.component_Plate[8],
            CI.component_Plate[3],
            CI.component_Plate[8],
            CI.component_Plate[5],
            pipeTier3,
            CI.component_Plate[5],
            CI.component_Plate[5],
            ItemList.Electric_Pump_LV.get(1),
            CI.component_Plate[5],
            GregtechItemList.GTFluidTank_MV.get(1));
        RecipeUtils.addShapedGregtechRecipe(
            "circuitPrimitive",
            "plateAluminium",
            "circuitPrimitive",
            "plateDarkSteel",
            "pipeMediumSteel",
            "plateDarkSteel",
            "circuitPrimitive",
            ItemList.Electric_Pump_MV.get(1L),
            "circuitPrimitive",
            GregtechItemList.GTFluidTank_HV.get(1));

        // Industrial Multi Tank
        // RECIPE_IndustrialMultiTankController = GregtechItemList.Industrial_MultiTank.get(1);
        RECIPE_IndustrialMultiTankFrame = GregtechItemList.Casing_MultitankExterior.get(1);

        // Industrial Multi Tank Casing
        RecipeUtils.addShapedGregtechRecipe(
            "stickGrisium",
            "plateGrisium",
            "stickGrisium",
            "plateGrisium",
            "frameGtGrisium",
            "plateGrisium",
            "plateGrisium",
            "plateGrisium",
            "plateGrisium",
            RECIPE_IndustrialMultiTankFrame);
        // Industrial Multi Tank
        RecipeUtils.addShapedGregtechRecipe(
            "pipeHugeTantalloy60",
            "gearGrisium",
            "pipeHugeTantalloy60",
            "circuitData",
            RECIPE_IndustrialMultiTankFrame,
            "circuitData",
            "plateDoubleGrisium",
            "rotorGrisium",
            "plateDoubleGrisium",
            RECIPE_IndustrialMultiTankController);

        // Semi-Fluid Generators
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(14),
                CI.getTieredMachineHull(1, 1),
                CI.getElectricMotor(1, 2),
                CI.getElectricPiston(1, 2),
                GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 1L),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LV, 1L),
                CI.getGear(1, 2))
            .itemOutputs(GregtechItemList.Generator_SemiFluid_LV.get(1))
            .fluidInputs(Materials.Plastic.getMolten(1 * INGOTS))
            .duration(30 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(14),
                CI.getTieredMachineHull(2, 1),
                CI.getElectricMotor(2, 2),
                CI.getElectricPiston(2, 2),
                GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.AnnealedCopper, 1L),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.MV, 1L),
                CI.getGear(2, 2))
            .itemOutputs(GregtechItemList.Generator_SemiFluid_MV.get(1))
            .fluidInputs(Materials.Plastic.getMolten(1 * INGOTS))
            .duration(30 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(14),
                CI.getTieredMachineHull(3, 1),
                CI.getElectricMotor(3, 2),
                CI.getElectricPiston(3, 2),
                GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 1L),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.HV, 1L),
                GTOreDictUnificator.get(OrePrefixes.gearGt, Materials.Chrome, 2))
            .itemOutputs(GregtechItemList.Generator_SemiFluid_HV.get(1))
            .fluidInputs(Materials.Plastic.getMolten(1 * INGOTS))
            .duration(30 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(14),
                CI.getTieredMachineHull(4, 1),
                CI.getElectricMotor(4, 2),
                CI.getElectricPiston(4, 2),
                GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Titanium, 1L),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.EV, 1L),
                CI.getGear(4, 2))
            .itemOutputs(GregtechItemList.Generator_SemiFluid_EV.get(1))
            .fluidInputs(Materials.Plastic.getMolten(1 * INGOTS))
            .duration(30 * SECONDS)
            .eut(TierEU.RECIPE_EV)
            .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(14),
                CI.getTieredMachineHull(5, 1),
                CI.getElectricMotor(5, 2),
                CI.getElectricPiston(5, 2),
                GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tungsten, 1L),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.IV, 1L),
                CI.getGear(5, 2))
            .itemOutputs(GregtechItemList.Generator_SemiFluid_IV.get(1))
            .fluidInputs(Materials.Polytetrafluoroethylene.getMolten(1 * INGOTS))
            .duration(30 * SECONDS)
            .eut(TierEU.RECIPE_IV)
            .addTo(assemblerRecipes);

        GTModHandler.addCraftingRecipe(
            GregtechItemList.Generator_SemiFluid_LV.get(1L),
            CI.bits,
            new Object[] { "PCP", "EME", "GWG", 'M', ItemList.Hull_LV, 'P', ItemList.Electric_Piston_LV, 'E',
                ItemList.Electric_Motor_LV, 'C', OrePrefixes.circuit.get(Materials.LV), 'W',
                OrePrefixes.cableGt01.get(Materials.Tin), 'G', MaterialsAlloy.TUMBAGA.getGear(2) });
        GTModHandler.addCraftingRecipe(
            GregtechItemList.Generator_SemiFluid_MV.get(1L),
            CI.bits,
            new Object[] { "PCP", "EME", "GWG", 'M', ItemList.Hull_MV, 'P', ItemList.Electric_Piston_MV, 'E',
                ItemList.Electric_Motor_MV, 'C', OrePrefixes.circuit.get(Materials.MV), 'W',
                OrePrefixes.cableGt01.get(Materials.AnnealedCopper), 'G', MaterialsAlloy.EGLIN_STEEL.getGear(2) });
        GTModHandler.addCraftingRecipe(
            GregtechItemList.Generator_SemiFluid_HV.get(1L),
            CI.bits,
            new Object[] { "PCP", "EME", "GWG", 'M', ItemList.Hull_HV, 'P', ItemList.Electric_Piston_HV, 'E',
                ItemList.Electric_Motor_HV, 'C', OrePrefixes.circuit.get(Materials.HV), 'W',
                OrePrefixes.cableGt01.get(Materials.Gold), 'G',
                GTOreDictUnificator.get(OrePrefixes.gearGt, Materials.Chrome, 1) });
        GTModHandler.addCraftingRecipe(
            GregtechItemList.Generator_SemiFluid_EV.get(1L),
            CI.bits,
            new Object[] { "PCP", "EME", "GWG", 'M', ItemList.Hull_EV, 'P', ItemList.Electric_Piston_EV, 'E',
                ItemList.Electric_Motor_EV, 'C', OrePrefixes.circuit.get(Materials.EV), 'W',
                OrePrefixes.cableGt01.get(Materials.Titanium), 'G', MaterialsAlloy.INCOLOY_DS.getGear(1) });
        GTModHandler.addCraftingRecipe(
            GregtechItemList.Generator_SemiFluid_IV.get(1L),
            CI.bits,
            new Object[] { "PCP", "EME", "GWG", 'M', ItemList.Hull_IV, 'P', ItemList.Electric_Piston_IV, 'E',
                ItemList.Electric_Motor_IV, 'C', OrePrefixes.circuit.get(Materials.IV), 'W',
                OrePrefixes.cableGt01.get(Materials.Tungsten), 'G', MaterialsAlloy.NITINOL_60.getGear(1) });

        // Industrial Blast Smelter
        RECIPE_IndustrialBlastSmelterController = GregtechItemList.Industrial_AlloyBlastSmelter.get(1);
        RECIPE_IndustrialBlastSmelterFrame = GregtechItemList.Casing_BlastSmelter.get(1);
        RECIPE_IndustrialBlastSmelterCoil = GregtechItemList.Casing_Coil_BlastSmelter.get(1);

        // Blast Smelter
        RecipeUtils.addShapedGregtechRecipe(
            "plateZirconiumCarbide",
            "circuitElite",
            "plateZirconiumCarbide",
            cableTier6,
            IV_MACHINE_AlloySmelter,
            cableTier6,
            "plateZirconiumCarbide",
            "circuitElite",
            "plateZirconiumCarbide",
            RECIPE_IndustrialBlastSmelterController);
        // Blast Smelter Frame Casing
        RecipeUtils.addShapedGregtechRecipe(
            "plateZirconiumCarbide",
            ToolDictNames.craftingToolHardHammer.name(),
            "plateZirconiumCarbide",
            "plateZirconiumCarbide",
            "frameGtZirconiumCarbide",
            "plateZirconiumCarbide",
            "plateZirconiumCarbide",
            ToolDictNames.craftingToolWrench.name(),
            "plateZirconiumCarbide",
            RECIPE_IndustrialBlastSmelterFrame);
        GTValues.RA.stdBuilder()
            .itemInputs(
                MaterialsAlloy.ZIRCONIUM_CARBIDE.getPlate(6),
                MaterialsAlloy.ZIRCONIUM_CARBIDE.getFrameBox(1),
                GTUtility.getIntegratedCircuit(1))
            .itemOutputs(RECIPE_IndustrialBlastSmelterFrame)
            .duration(2 * SECONDS + 10 * TICKS)
            .eut(16)
            .addTo(assemblerRecipes);

        // Blast Smelter Coil
        RecipeUtils.addShapedGregtechRecipe(
            "plateStaballoy",
            "plateStaballoy",
            "plateStaballoy",
            "frameGtStaballoy",
            CI.gearboxCasing_Tier_3,
            "frameGtStaballoy",
            "plateStaballoy",
            "plateStaballoy",
            "plateStaballoy",
            RECIPE_IndustrialBlastSmelterCoil);
        GTValues.RA.stdBuilder()
            .itemInputs(
                MaterialsAlloy.STABALLOY.getPlate(6),
                MaterialsAlloy.STABALLOY.getFrameBox(2),
                CI.gearboxCasing_Tier_3,
                GTUtility.getIntegratedCircuit(1))
            .itemOutputs(RECIPE_IndustrialBlastSmelterCoil)
            .duration(2 * SECONDS + 10 * TICKS)
            .eut(16)
            .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Casing_Coil_Infinity.get(1),
                ItemList.Reactor_Coolant_Sp_6.get(4),
                MaterialsAlloy.LAURENIUM.getPlateDouble(2),
                CustomItemList.eM_Coil.get(1))
            .itemOutputs(GregtechItemList.Casing_Coil_QuantumForceTransformer.get(1))
            .fluidInputs(MaterialsAlloy.QUANTUM.getFluidStack(4 * INGOTS))
            .duration(1 * MINUTES + 30 * SECONDS)
            .eut(TierEU.RECIPE_LuV)
            .addTo(assemblerRecipes);

        TTRecipeAdder.addResearchableAssemblylineRecipe(
            GregtechItemList.Casing_Coil_QuantumForceTransformer.get(1),
            2048 * 120 * 20,
            2048,
            (int) GTValues.VP[11],
            16,
            new Object[] { GregtechItemList.Controller_MolecularTransformer.get(1),
                GTModHandler.getModItem(EternalSingularity.ID, "eternal_singularity", 1),
                new Object[] { OrePrefixes.circuit.get(Materials.UEV), 8 }, ItemList.Electric_Pump_UEV.get(4),
                ItemList.Field_Generator_UEV.get(4), GregtechItemList.Laser_Lens_Special.get(1) },
            new FluidStack[] { MaterialMisc.MUTATED_LIVING_SOLDER.getFluidStack(10 * INGOTS),
                MaterialsAlloy.PIKYONIUM.getFluidStack(32 * INGOTS) },
            GregtechItemList.QuantumForceTransformer.get(1),
            3 * MINUTES,
            (int) TierEU.RECIPE_UIV);

        // Industrial Matter Fabricator
        RECIPE_IndustrialMatterFabController = GregtechItemList.Industrial_MassFab.get(1);
        RECIPE_IndustrialMatterFabFrame = GregtechItemList.Casing_MatterFab.get(1);
        RECIPE_IndustrialMatterFabCoil = GregtechItemList.Casing_MatterGen.get(1);

        // Matter Fabricator CPU
        RecipeUtils.addShapedGregtechRecipe(
            CI.getPlate(8, 1),
            "circuitSuperconductor",
            CI.getPlate(8, 1),
            GTOreDictUnificator.get(OrePrefixes.cableGt04.get(Materials.NaquadahAlloy), 1L),
            ItemList.Casing_UV.get(1),
            GTOreDictUnificator.get(OrePrefixes.cableGt04.get(Materials.NaquadahAlloy), 1L),
            CI.getPlate(8, 1),
            "circuitSuperconductor",
            CI.getPlate(8, 1),
            RECIPE_IndustrialMatterFabController);
        // Matter Fabricator Frame Casing
        RecipeUtils.addShapedGregtechRecipe(
            "plateNiobiumCarbide",
            CI.component_Rod[8],
            "plateNiobiumCarbide",
            CI.component_Rod[8],
            "frameGtInconel690",
            CI.component_Rod[8],
            "plateNiobiumCarbide",
            CI.component_Rod[8],
            "plateNiobiumCarbide",
            RECIPE_IndustrialMatterFabFrame);
        GTValues.RA.stdBuilder()
            .itemInputs(
                MaterialsAlloy.NIOBIUM_CARBIDE.getPlate(4),
                MaterialsAlloy.INCONEL_792.getRod(4),
                MaterialsAlloy.INCONEL_690.getFrameBox(1))
            .itemOutputs(RECIPE_IndustrialMatterFabFrame)
            .duration(2 * SECONDS + 10 * TICKS)
            .eut(16)
            .addTo(assemblerRecipes);

        // Matter Fabricator Coil
        RecipeUtils.addShapedGregtechRecipe(
            CI.getPlate(6, 1),
            CI.getPlate(7, 1),
            CI.getPlate(6, 1),
            "frameGtStellite",
            ItemList.Casing_UV.get(1),
            "frameGtStellite",
            CI.getPlate(6, 1),
            CI.getPlate(7, 1),
            CI.getPlate(6, 1),
            RECIPE_IndustrialMatterFabCoil);
        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Casing_UV.get(1),
                MaterialsAlloy.ZERON_100.getPlate(4),
                MaterialsAlloy.PIKYONIUM.getPlate(2),
                MaterialsAlloy.STELLITE.getFrameBox(2),
                GTUtility.getIntegratedCircuit(1))
            .itemOutputs(RECIPE_IndustrialMatterFabCoil)
            .duration(2 * SECONDS + 10 * TICKS)
            .eut(16)
            .addTo(assemblerRecipes);

        // Industrial Sieve
        RECIPE_IndustrialSieveController = GregtechItemList.Industrial_Sifter.get(1);
        RECIPE_IndustrialSieveFrame = GregtechItemList.Casing_Sifter.get(1);
        RECIPE_IndustrialSieveGrate = GregtechItemList.Casing_SifterGrate.get(1);

        // Industrial Sieve
        RecipeUtils.addShapedGregtechRecipe(
            "plateEglinSteel",
            "circuitAdvanced",
            "plateEglinSteel",
            cableTier4,
            HV_MACHINE_Sifter,
            cableTier4,
            "plateEglinSteel",
            "circuitAdvanced",
            "plateEglinSteel",
            RECIPE_IndustrialSieveController);
        // Industrial Sieve Casing
        RecipeUtils.addShapedGregtechRecipe(
            "plateEglinSteel",
            "plateEglinSteel",
            "plateEglinSteel",
            "plateEglinSteel",
            "frameGtTumbaga",
            "plateEglinSteel",
            "plateEglinSteel",
            "plateEglinSteel",
            "plateEglinSteel",
            RECIPE_IndustrialSieveFrame);
        GTValues.RA.stdBuilder()
            .itemInputs(
                MaterialsAlloy.EGLIN_STEEL.getPlate(8),
                MaterialsAlloy.TUMBAGA.getFrameBox(1),
                GTUtility.getIntegratedCircuit(1))
            .itemOutputs(RECIPE_IndustrialSieveFrame)
            .duration(2 * SECONDS + 10 * TICKS)
            .eut(16)
            .addTo(assemblerRecipes);

        // Industrial Sieve Grate
        RecipeUtils.addShapedGregtechRecipe(
            "frameGtEglinSteel",
            "wireFineSteel",
            "frameGtEglinSteel",
            "wireFineSteel",
            "wireFineSteel",
            "wireFineSteel",
            "frameGtEglinSteel",
            "wireFineSteel",
            "frameGtEglinSteel",
            RECIPE_IndustrialSieveGrate);
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Steel, 5),
                MaterialsAlloy.EGLIN_STEEL.getFrameBox(4),
                GTUtility.getIntegratedCircuit(1))
            .itemOutputs(RECIPE_IndustrialSieveGrate)
            .duration(2 * SECONDS + 10 * TICKS)
            .eut(16)
            .addTo(assemblerRecipes);

        // Industrial Tree Farmer
        RECIPE_TreeFarmController = GregtechItemList.Industrial_TreeFarm.get(1);
        RECIPE_TreeFarmFrame = GregtechItemList.Casing_PLACEHOLDER_TreeFarmer.get(1);
        // Industrial Tree Farm Controller
        RecipeUtils.addShapedGregtechRecipe(
            ItemList.Field_Generator_IV.get(1),
            MaterialsAlloy.INCOLOY_MA956.getRotor(1),
            ItemList.Field_Generator_IV.get(1),
            MaterialsAlloy.NITINOL_60.getPlate(1),
            GregtechItemList.GTPP_Casing_IV.get(1),
            MaterialsAlloy.NITINOL_60.getPlate(1),
            ItemList.Field_Generator_IV.get(1),
            MaterialsAlloy.INCONEL_792.getComponentByPrefix(OrePrefixes.pipeMedium, 1),
            ItemList.Field_Generator_IV.get(1),
            RECIPE_TreeFarmController);
        // Industrial Tree Farm Frame
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(2),
                MaterialsAlloy.TUMBAGA.getFrameBox(1),
                ItemUtils.getItemStackOfAmountFromOreDict("pipeTinySteel", 1),
                ItemList.MV_Coil.get(1),
                ItemList.IC2_Plantball.get(4),
                GTOreDictUnificator.get(OrePrefixes.plank, Materials.Wood, 8))
            .itemOutputs(RECIPE_TreeFarmFrame)
            .fluidInputs(GTModHandler.getDistilledWater(2_000))
            .duration(10 * SECONDS)
            .eut(64)
            .addTo(assemblerRecipes);

        // Tesseracts
        RECIPE_TesseractGenerator = GregtechItemList.GT4_Tesseract_Generator.get(1);
        RECIPE_TesseractTerminal = GregtechItemList.GT4_Tesseract_Terminal.get(1);
        // Tesseract Generator
        RecipeUtils.addShapedGregtechRecipe(
            "plateTitanium",
            "circuitMaster",
            "plateTitanium",
            "circuitMaster",
            new ItemStack(Blocks.ender_chest),
            "circuitMaster",
            "plateTitanium",
            GregtechItemList.Gregtech_Computer_Cube.get(1),
            "plateTitanium",
            RECIPE_TesseractGenerator);
        // Tesseract Terminal
        RecipeUtils.addShapedGregtechRecipe(
            "plateTitanium",
            "circuitElite",
            "plateTitanium",
            "circuitElite",
            new ItemStack(Blocks.ender_chest),
            "circuitElite",
            "plateTitanium",
            ItemList.Hull_EV.get(1),
            "plateTitanium",
            RECIPE_TesseractTerminal);

        // simple washer
        final List<ItemStack> washers = ImmutableList.of(
            GregtechItemList.SimpleDustWasher_LV.get(1),
            GregtechItemList.SimpleDustWasher_MV.get(1),
            GregtechItemList.SimpleDustWasher_HV.get(1),
            GregtechItemList.SimpleDustWasher_EV.get(1),
            GregtechItemList.SimpleDustWasher_IV.get(1),
            GregtechItemList.SimpleDustWasher_LuV.get(1),
            GregtechItemList.SimpleDustWasher_ZPM.get(1),
            GregtechItemList.SimpleDustWasher_UV.get(1));

        for (int i = 0; i < washers.size(); i++) {
            final int tier = i + 1;
            GTValues.RA.stdBuilder()
                .itemInputs(
                    CI.getTieredMachineHull(tier),
                    CI.getTieredComponent(OrePrefixes.screw, tier, tier * 4),
                    CI.getTieredComponent(OrePrefixes.plate, tier - 1, tier * 2),
                    CI.getTieredComponent(OrePrefixes.rod, tier, tier),
                    CI.getTieredComponent(OrePrefixes.circuit, tier, 1))
                .itemOutputs(washers.get(i))
                .fluidInputs(CI.getTieredFluid(tier, tier * INGOTS))
                .duration(20 * 5 * tier)
                .eut(GTValues.VP[tier])
                .addTo(assemblerRecipes);

        }

        if (GTMod.proxy.mPollution) {
            RecipeUtils.addShapedGregtechRecipe(
                "plateCarbon",
                "plateCarbon",
                "plateCarbon",
                "dustCarbon",
                "dustCarbon",
                "dustCarbon",
                "plateCarbon",
                "plateCarbon",
                "plateCarbon",
                new ItemStack(ModItems.itemAirFilter, 1, 0));

            RecipeUtils.addShapedGregtechRecipe(
                "plateCarbon",
                "plateCarbon",
                "plateCarbon",
                "cellLithiumPeroxide",
                "dustCarbon",
                "cellLithiumPeroxide",
                "plateCarbon",
                "plateCarbon",
                "plateCarbon",
                new ItemStack(ModItems.itemAirFilter, 1, 1));

            // Pollution Detector
            RecipeUtils.addShapedGregtechRecipe(
                "plateSteel",
                ItemList.Sensor_LV.get(1),
                "plateSteel",
                "plateSteel",
                ItemList.Electric_Motor_LV.get(1),
                "plateSteel",
                "circuitBasic",
                ItemList.Hull_LV.get(1),
                "circuitBasic",
                GregtechItemList.Pollution_Detector.get(1));

            // LV
            RecipeUtils.addShapedGregtechRecipe(
                CI.component_Plate[1],
                new ItemStack(ModItems.itemAirFilter, 1),
                CI.component_Plate[1],
                CI.component_Plate[1],
                ItemList.Electric_Motor_LV.get(1),
                CI.component_Plate[1],
                "circuitBasic",
                ItemList.Hull_LV.get(1),
                "circuitBasic",
                GregtechItemList.Pollution_Cleaner_LV.get(1));
            // MV
            RecipeUtils.addShapedGregtechRecipe(
                CI.component_Plate[2],
                new ItemStack(ModItems.itemAirFilter, 1),
                CI.component_Plate[2],
                CI.component_Plate[2],
                ItemList.Electric_Motor_MV.get(1),
                CI.component_Plate[2],
                "circuitGood",
                ItemList.Hull_MV.get(1),
                "circuitGood",
                GregtechItemList.Pollution_Cleaner_MV.get(1));
            // HV
            RecipeUtils.addShapedGregtechRecipe(
                CI.component_Plate[3],
                new ItemStack(ModItems.itemAirFilter, 1),
                CI.component_Plate[3],
                CI.component_Plate[3],
                ItemList.Electric_Motor_HV.get(1),
                CI.component_Plate[3],
                "circuitAdvanced",
                ItemList.Hull_HV.get(1),
                "circuitAdvanced",
                GregtechItemList.Pollution_Cleaner_HV.get(1));
            // EV
            RecipeUtils.addShapedGregtechRecipe(
                CI.component_Plate[4],
                new ItemStack(ModItems.itemAirFilter, 1),
                CI.component_Plate[4],
                CI.component_Plate[4],
                ItemList.Electric_Motor_EV.get(1),
                CI.component_Plate[4],
                "circuitData",
                ItemList.Hull_EV.get(1),
                "circuitData",
                GregtechItemList.Pollution_Cleaner_EV.get(1));
            // IV
            RecipeUtils.addShapedGregtechRecipe(
                CI.component_Plate[5],
                new ItemStack(ModItems.itemAirFilter, 1, 1),
                CI.component_Plate[5],
                CI.component_Plate[5],
                ItemList.Electric_Motor_IV.get(1),
                CI.component_Plate[5],
                "circuitElite",
                ItemList.Hull_IV.get(1),
                "circuitElite",
                GregtechItemList.Pollution_Cleaner_IV.get(1));
            // LuV
            RecipeUtils.addShapedGregtechRecipe(
                CI.component_Plate[6],
                new ItemStack(ModItems.itemAirFilter, 1, 1),
                CI.component_Plate[6],
                CI.component_Plate[6],
                ItemList.Electric_Motor_LuV.get(1),
                CI.component_Plate[6],
                "circuitMaster",
                ItemList.Hull_LuV.get(1),
                "circuitMaster",
                GregtechItemList.Pollution_Cleaner_LuV.get(1));
            // ZPM
            RecipeUtils.addShapedGregtechRecipe(
                CI.component_Plate[7],
                new ItemStack(ModItems.itemAirFilter, 1, 1),
                CI.component_Plate[7],
                CI.component_Plate[7],
                ItemList.Electric_Motor_ZPM.get(1),
                CI.component_Plate[7],
                "circuitUltimate",
                ItemList.Hull_ZPM.get(1),
                "circuitUltimate",
                GregtechItemList.Pollution_Cleaner_ZPM.get(1));
            // UV
            RecipeUtils.addShapedGregtechRecipe(
                CI.component_Plate[8],
                new ItemStack(ModItems.itemAirFilter, 1, 1),
                CI.component_Plate[8],
                CI.component_Plate[8],
                ItemList.Electric_Motor_UV.get(1),
                CI.component_Plate[8],
                "circuitSuperconductor",
                ItemList.Hull_UV.get(1),
                "circuitSuperconductor",
                GregtechItemList.Pollution_Cleaner_UV.get(1));
            // MAX
            RecipeUtils.addShapedGregtechRecipe(
                CI.component_Plate[9],
                new ItemStack(ModItems.itemAirFilter, 1, 1),
                CI.component_Plate[9],
                CI.component_Plate[9],
                ItemList.Electric_Motor_UHV.get(1),
                CI.component_Plate[9],
                "circuitInfinite",
                ItemList.Hull_MAX.get(1),
                "circuitInfinite",
                GregtechItemList.Pollution_Cleaner_MAX.get(1));
        }

        RECIPE_ThermalBoilerController = GregtechItemList.GT4_Thermal_Boiler.get(1);
        RECIPE_ThermalBoilerCasing = GregtechItemList.Casing_ThermalContainment.get(2);
        ItemStack centrifugeEV = ItemList.Machine_EV_Centrifuge.get(1);

        RecipeUtils.addShapedGregtechRecipe(
            getModItem(RemoteIO.ID, "tile.machine", 1, 1),
            ItemList.Machine_HV_Centrifuge.get(1L),
            getModItem(RemoteIO.ID, "tile.machine", 1, 1),
            "gearGtTungstenSteel",
            "circuitElite",
            "gearGtTungstenSteel",
            getModItem(RemoteIO.ID, "tile.machine", 1, 1),
            ItemList.Machine_HV_Centrifuge.get(1L),
            getModItem(RemoteIO.ID, "tile.machine", 1, 1),
            RECIPE_ThermalBoilerController);

        RecipeUtils.addShapedGregtechRecipe(
            MaterialsAlloy.MARAGING350.getPlate(1),
            "plateStainlessSteel",
            MaterialsAlloy.MARAGING350.getPlate(1),
            "circuitAdvanced",
            ItemList.Casing_HV.get(1),
            "circuitAdvanced",
            MaterialsAlloy.MARAGING350.getPlate(1),
            MaterialsAlloy.MARAGING350.getPlate(1),
            MaterialsAlloy.MARAGING350.getPlate(1),
            RECIPE_ThermalBoilerCasing);

        // Lava Filter Recipe
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(18),
                ItemUtils.getItemStackOfAmountFromOreDict("dustCarbon", 32),
                ItemUtils.getItemStackOfAmountFromOreDict("wireFineSteel", 32),
                ItemUtils.getItemStackOfAmountFromOreDict("ringTumbaga", 16),
                ItemUtils.getItemStackOfAmountFromOreDict("foilCopper", 4),
                getModItem(Mods.IndustrialCraft2.ID, "itemPartCarbonMesh", 64, 0))
            .itemOutputs(new ItemStack(ModItems.itemLavaFilter, 16))
            .fluidInputs(CI.getTieredFluid(3, 1 * INGOTS))
            .duration(1 * MINUTES + 20 * SECONDS)
            .eut(240)
            .addTo(assemblerRecipes);

        // Air Intake Hatch
        RecipeUtils.addShapedGregtechRecipe(
            CI.component_Plate[6],
            ItemList.Casing_Grate.get(1),
            CI.component_Plate[6],
            CI.component_Plate[6],
            CI.getFluidRegulator(5, 1),
            CI.component_Plate[6],
            "circuitElite",
            ItemList.Hatch_Input_IV.get(1),
            "circuitElite",
            GregtechItemList.Hatch_Air_Intake.get(1));

        RecipeUtils.addShapedGregtechRecipe(
            CI.getPlate(7, 1),
            GregtechItemList.Hatch_Air_Intake.get(1),
            CI.getPlate(7, 1),
            CI.getPlate(7, 1),
            CI.getFluidRegulator(7, 1),
            CI.getPlate(7, 1),
            "circuitUltimate",
            ItemList.Hatch_Input_ZPM.get(1),
            "circuitUltimate",
            GregtechItemList.Hatch_Air_Intake_Extreme.get(1));

        RecipeUtils.addShapedGregtechRecipe(
            MaterialsAlloy.OCTIRON.getPlate(1),
            GregtechItemList.Hatch_Air_Intake_Extreme.get(1),
            MaterialsAlloy.OCTIRON.getPlate(1),
            MaterialsAlloy.OCTIRON.getPlate(1),
            ItemList.FluidRegulator_UHV.get(1),
            MaterialsAlloy.OCTIRON.getPlate(1),
            "circuitInfinite",
            ItemList.Hatch_Input_UHV.get(1),
            "circuitInfinite",
            GregtechItemList.Hatch_Air_Intake_Atmospheric.get(1));

        // Thorium Reactor
        RECIPE_LFTRController = GregtechItemList.ThoriumReactor.get(1);
        RECIPE_LFTRInnerCasing = GregtechItemList.Casing_Reactor_II.get(1); // Zeron
        RECIPE_LFTROuterCasing = GregtechItemList.Casing_Reactor_I.get(1); // Hastelloy

        ItemStack controlCircuit = new ItemStack(ModItems.itemCircuitLFTR);
        RecipeUtils.addShapedGregtechRecipe(
            controlCircuit,
            "cableGt12Naquadah",
            controlCircuit,
            "plateDoubleHastelloyN",
            GregtechItemList.Gregtech_Computer_Cube.get(1),
            "plateDoubleHastelloyN",
            "plateThorium232",
            ItemList.Hull_IV.get(1),
            "plateThorium232",
            RECIPE_LFTRController);

        // Reactor Shield Casing
        RecipeUtils.addShapedGregtechRecipe(
            "plateDoubleHastelloyC276",
            ToolDictNames.craftingToolScrewdriver.name(),
            "plateDoubleHastelloyC276",
            "gearGtTalonite",
            ItemList.Field_Generator_LV.get(1),
            "gearGtTalonite",
            "plateDoubleHastelloyC276",
            ToolDictNames.craftingToolHardHammer.name(),
            "plateDoubleHastelloyC276",
            RECIPE_LFTRInnerCasing);
        GTValues.RA.stdBuilder()
            .itemInputs(
                MaterialsAlloy.HASTELLOY_C276.getPlateDouble(4),
                MaterialsAlloy.TALONITE.getGear(2),
                ItemList.Field_Generator_LV.get(1))
            .itemOutputs(RECIPE_LFTRInnerCasing)
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(assemblerRecipes);

        // Hastelloy-N Reactor Casing
        ItemStack IC2HeatPlate = getModItem(Mods.IndustrialCraft2.ID, "reactorPlatingHeat", 1);
        RecipeUtils.addShapedGregtechRecipe(
            "plateDoubleHastelloyN",
            IC2HeatPlate,
            "plateDoubleHastelloyN",
            IC2HeatPlate,
            "frameGtHastelloyC276",
            IC2HeatPlate,
            "plateDoubleHastelloyN",
            IC2HeatPlate,
            "plateDoubleHastelloyN",
            RECIPE_LFTROuterCasing);

        // LFTR Control Circuit
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LuV, 1),
                ItemList.Field_Generator_HV.get(1))
            .itemOutputs(controlCircuit)
            .duration(4 * MINUTES)
            .eut(TierEU.RECIPE_HV)
            .addTo(assemblerRecipes);

        // Fission Fuel Plant
        RecipeUtils.addShapedGregtechRecipe(
            "circuitElite",
            ToolDictNames.craftingToolSolderingIron.name(),
            "circuitElite",
            "plateDenseTungstenSteel",
            GregtechItemList.Gregtech_Computer_Cube.get(1),
            "plateDenseTungstenSteel",
            "gearGtStellite",
            ItemList.Hull_IV.get(1),
            "gearGtStellite",
            GregtechItemList.Industrial_FuelRefinery.get(1));

        ItemStack mInnerTank = ItemList.Super_Tank_IV.get(1);

        // Incoloy Casing
        RecipeUtils.addShapedGregtechRecipe(
            "plateIncoloyDS",
            "pipeHugeStaballoy",
            "plateIncoloyDS",
            "gearGtIncoloyDS",
            mInnerTank,
            "gearGtIncoloyDS",
            "plateIncoloyDS",
            "pipeHugeStaballoy",
            "plateIncoloyDS",
            GregtechItemList.Casing_Refinery_Internal.get(1));

        // Hastelloy-N Sealant Casing
        RecipeUtils.addShapedGregtechRecipe(
            "plateIncoloyMA956",
            "plateHastelloyN",
            "plateIncoloyMA956",
            "plateHastelloyN",
            "frameGtHastelloyC276",
            "plateHastelloyN",
            "plateIncoloyMA956",
            "plateHastelloyN",
            "plateIncoloyMA956",
            GregtechItemList.Casing_Refinery_External.get(1));
        GTValues.RA.stdBuilder()
            .itemInputs(
                MaterialsAlloy.HASTELLOY_N.getPlate(4),
                MaterialsAlloy.INCOLOY_MA956.getPlate(4),
                MaterialsAlloy.HASTELLOY_C276.getFrameBox(1))
            .itemOutputs(GregtechItemList.Casing_Refinery_External.get(1))
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(assemblerRecipes);

        // Hastelloy-X Structural Casing
        RecipeUtils.addShapedGregtechRecipe(
            "ringInconel792",
            "gearGtHastelloyX",
            CI.component_Plate[5],
            ToolDictNames.craftingToolHardHammer.name(),
            "frameGtHastelloyC276",
            ToolDictNames.craftingToolWrench.name(),
            CI.component_Plate[5],
            CI.getTieredMachineCasing(4),
            "ringInconel792",
            GregtechItemList.Casing_Refinery_Structural.get(1));

        RecipeUtils.addShapedGregtechRecipe(
            CI.getPlate(5, 1),
            MaterialsAlloy.HASTELLOY_X.getPlateDouble(1),
            CI.getPlate(5, 1),
            CI.getPlate(5, 1),
            CI.getTieredMachineCasing(5),
            CI.getPlate(5, 1),
            CI.getRobotArm(5, 1),
            ItemList.Casing_FrostProof.get(1),
            CI.getRobotArm(5, 1),
            GregtechItemList.ColdTrap_IV.get(1));
        RecipeUtils.addShapedGregtechRecipe(
            CI.getPlate(7, 1),
            MaterialsAlloy.HS188A.getPlateDouble(1),
            CI.getPlate(7, 1),
            CI.getPlate(7, 1),
            GregtechItemList.ColdTrap_IV.get(1),
            CI.getPlate(7, 1),
            CI.getRobotArm(7, 1),
            ItemList.Casing_FrostProof.get(1),
            CI.getRobotArm(7, 1),
            GregtechItemList.ColdTrap_ZPM.get(1));

        RecipeUtils.addShapedGregtechRecipe(
            CI.getFieldGenerator(3, 1),
            CI.getRobotArm(5, 1),
            CI.getPlate(5, 1),
            MaterialsAlloy.HASTELLOY_N.getPlateDouble(1),
            ItemList.Machine_IV_ChemicalReactor.get(1),
            MaterialsAlloy.HASTELLOY_N.getPlateDouble(1),
            CI.getPlate(5, 1),
            MaterialsAlloy.HASTELLOY_N.getPlateDouble(1),
            CI.getFieldGenerator(3, 1),
            GregtechItemList.ReactorProcessingUnit_IV.get(1));
        RecipeUtils.addShapedGregtechRecipe(
            CI.getFieldGenerator(5, 1),
            CI.getRobotArm(7, 1),
            CI.getPlate(7, 1),
            MaterialsAlloy.HS188A.getPlateDouble(1),
            GregtechItemList.ReactorProcessingUnit_IV.get(1),
            MaterialsAlloy.HS188A.getPlateDouble(1),
            CI.getPlate(7, 1),
            MaterialsAlloy.HS188A.getPlateDouble(1),
            CI.getFieldGenerator(5, 1),
            GregtechItemList.ReactorProcessingUnit_ZPM.get(1));

        // Nuclear Salt Processing Plant Controller
        RECIPE_SaltPlantController = GregtechItemList.Nuclear_Salt_Processing_Plant.get(1);

        RecipeUtils.addShapedGregtechRecipe(
            "plateOsmiridium",
            GregtechItemList.ReactorProcessingUnit_IV.get(1),
            "plateOsmiridium",
            "plateRuridit",
            "circuitUltimate",
            "plateRuridit",
            "plateOsmiridium",
            GregtechItemList.ColdTrap_IV.get(1),
            "plateOsmiridium",
            RECIPE_SaltPlantController);

        // Cyclotron
        RECIPE_CyclotronController = GregtechItemList.COMET_Cyclotron.get(1);
        RECIPE_CyclotronOuterCasing = GregtechItemList.Casing_Cyclotron_External.get(1);
        RECIPE_CyclotronInnerCoil = GregtechItemList.Casing_Cyclotron_Coil.get(1);

        // Outer Casing
        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Casing_FrostProof.get(1),
                GregtechItemList.DehydratorCoilWireEV.get(4),
                MaterialsAlloy.INCOLOY_DS.getPlate(8),
                MaterialsAlloy.INCONEL_690.getScrew(16),
                MaterialsAlloy.EGLIN_STEEL.getLongRod(4),
                CI.getElectricPiston(3, 2))
            .itemOutputs(RECIPE_CyclotronOuterCasing)
            .fluidInputs(MaterialsAlloy.ZIRCONIUM_CARBIDE.getFluidStack(8 * INGOTS))
            .duration(60 * SECONDS)
            .eut(TierEU.RECIPE_EV)
            .addTo(assemblerRecipes);
        // Inner Coil
        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Casing_Coil_Nichrome.get(1),
                GregtechItemList.DehydratorCoilWireIV.get(8),
                MaterialsAlloy.INCOLOY_MA956.getPlate(8),
                MaterialsAlloy.TANTALLOY_61.getBolt(16),
                MaterialsAlloy.INCOLOY_020.getScrew(32),
                CI.getFieldGenerator(4, 1))
            .itemOutputs(RECIPE_CyclotronInnerCoil)
            .fluidInputs(MaterialsAlloy.HG1223.getFluidStack(5 * INGOTS))
            .duration(2 * MINUTES)
            .eut(TierEU.RECIPE_IV)
            .addTo(assemblerRecipes);
        // Controller
        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Hull_IV.get(1),
                GTUtility.copyAmount(2, RECIPE_CyclotronInnerCoil),
                MaterialsAlloy.INCOLOY_020.getPlate(8),
                MaterialsAlloy.TANTALLOY_61.getGear(2),
                MaterialsAlloy.INCOLOY_MA956.getScrew(16),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.IV, 16))
            .itemOutputs(RECIPE_CyclotronController)
            .fluidInputs(MaterialsAlloy.INCOLOY_020.getFluidStack(9 * INGOTS))
            .duration(5 * MINUTES)
            .eut(TierEU.RECIPE_IV)
            .addTo(assemblerRecipes);

        // Mazut
        GTModHandler.addCraftingRecipe(
            GregtechItemList.Controller_LargeSemifluidGenerator.get(1L),
            CI.bitsd,
            new Object[] { "PCP", "EME", "GWG", 'M', ItemList.Hull_EV, 'P', ItemList.Electric_Piston_EV, 'E',
                ItemList.Electric_Pump_EV, 'C', OrePrefixes.circuit.get(Materials.EV), 'W',
                OrePrefixes.cableGt08.get(Materials.Electrum), 'G', MaterialsAlloy.INCONEL_792.getGear(1) });

        RecipeUtils.addShapedRecipe(
            "screwTitanium",
            "plateIncoloy020",
            "screwTitanium",
            "plateIncoloy020",
            "frameGtIncoloyMA956",
            "plateIncoloy020",
            "screwTitanium",
            "plateIncoloy020",
            "screwTitanium",
            GregtechItemList.Casing_Power_SubStation.get(1));

        ItemStack mBattery = new ItemStack(ModItems.itemCircuitLFTR);

        RecipeUtils.addShapedRecipe(
            "plateIncoloyMA956",
            mBattery,
            "plateIncoloyMA956",
            GregtechItemList.Casing_Power_SubStation.get(1),
            GregtechItemList.Casing_Vanadium_Redox.get(1),
            GregtechItemList.Casing_Power_SubStation.get(1),
            "plateIncoloy020",
            "plateIncoloyMA956",
            "plateIncoloy020",
            GregtechItemList.PowerSubStation.get(1));

        RecipeUtils.addShapedRecipe(
            "plateRedSteel",
            ToolDictNames.craftingToolHardHammer.name(),
            "plateRedSteel",
            "plateRedSteel",
            "frameGtBlackSteel",
            "plateRedSteel",
            "plateRedSteel",
            ToolDictNames.craftingToolWrench.name(),
            "plateRedSteel",
            GregtechItemList.Casing_ThermalCentrifuge.get(1));
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.RedSteel, 6),
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.BlackSteel, 1),
                GTUtility.getIntegratedCircuit(1))
            .itemOutputs(GregtechItemList.Casing_ThermalCentrifuge.get(1L))
            .duration(2 * SECONDS + 10 * TICKS)
            .eut(16)
            .addTo(assemblerRecipes);

        RecipeUtils.addShapedRecipe(
            "plateRedSteel",
            "circuitData",
            "plateRedSteel",
            "stickTalonite",
            EV_MACHINE_ThermalCentrifuge,
            "stickTalonite",
            "plateRedSteel",
            "gearGtTalonite",
            "plateRedSteel",
            GregtechItemList.Industrial_ThermalCentrifuge.get(1));

        RecipeUtils.addShapedRecipe(
            "plateGrisium",
            ToolDictNames.craftingToolHardHammer.name(),
            "plateGrisium",
            "plateTalonite",
            "frameGtGrisium",
            "plateTalonite",
            "plateGrisium",
            ToolDictNames.craftingToolWrench.name(),
            "plateGrisium",
            GregtechItemList.Casing_WashPlant.get(1));
        GTValues.RA.stdBuilder()
            .itemInputs(
                MaterialsAlloy.LEAGRISIUM.getPlate(4),
                MaterialsAlloy.TALONITE.getPlate(2),
                MaterialsAlloy.LEAGRISIUM.getFrameBox(1),
                GTUtility.getIntegratedCircuit(1))
            .itemOutputs(GregtechItemList.Casing_WashPlant.get(1L))
            .duration(2 * SECONDS + 10 * TICKS)
            .eut(16)
            .addTo(assemblerRecipes);

        RecipeUtils.addShapedRecipe(
            "plateGrisium",
            EV_MACHINE_OreWasher,
            "plateGrisium",
            "plateTalonite",
            "circuitData",
            "plateTalonite",
            "plateGrisium",
            EV_MACHINE_ChemicalBath,
            "plateGrisium",
            GregtechItemList.Industrial_WashPlant.get(1));

        GTValues.RA.stdBuilder()
            .itemInputs(
                GregtechItemList.Casing_Multi_Use.get(1),
                ItemList.Block_IridiumTungstensteel.get(1),
                CI.getTieredComponent(OrePrefixes.circuit, 2, 16),
                CI.getTieredComponent(OrePrefixes.screw, 5, 32),
                CI.getTieredComponent(OrePrefixes.bolt, 5, 12),
                CI.getTieredComponent(OrePrefixes.plate, 6, 8))
            .itemOutputs(GregtechItemList.Casing_Autocrafter.get(1))
            .fluidInputs(CI.getTertiaryTieredFluid(6, 4 * INGOTS))
            .duration(2 * MINUTES)
            .eut(TierEU.RECIPE_IV)
            .addTo(assemblerRecipes);
        GTValues.RA.stdBuilder()
            .itemInputs(
                GregtechItemList.Casing_Refinery_Structural.get(4),
                new ItemStack(ModItems.itemCircuitLFTR, 1),
                CI.getTieredComponent(OrePrefixes.cableGt08, 6, 16),
                CI.getTransmissionComponent(5, 2),
                GregtechItemList.Gregtech_Computer_Cube.get(1))
            .itemOutputs(GregtechItemList.GT4_Multi_Crafter.get(1))
            .fluidInputs(CI.getTieredFluid(7, 8 * INGOTS))
            .duration(5 * MINUTES)
            .eut(TierEU.RECIPE_IV)
            .addTo(assemblerRecipes);
        GTValues.RA.stdBuilder()
            .itemInputs(
                GregtechItemList.Casing_Multi_Use.get(1),
                CI.getEmitter(4, 2),
                CI.getRobotArm(4, 2),
                CI.getTieredComponent(OrePrefixes.circuit, 2, 8),
                CI.getTieredComponent(OrePrefixes.screw, 3, 8),
                CI.getTieredComponent(OrePrefixes.plate, 5, 4))
            .itemOutputs(new ItemStack(ModBlocks.blockProjectTable))
            .fluidInputs(CI.getAlternativeTieredFluid(5, 4 * INGOTS))
            .duration(1 * MINUTES + 30 * SECONDS)
            .eut(TierEU.RECIPE_EV)
            .addTo(assemblerRecipes);

        ItemStack plate = MaterialsAlloy.MARAGING300.getPlate(1);
        RecipeUtils.addShapedRecipe(
            plate,
            ToolDictNames.craftingToolHardHammer.name(),
            plate,
            "plateStellite",
            "frameGtTalonite",
            "plateStellite",
            plate,
            ToolDictNames.craftingToolWrench.name(),
            plate,
            GregtechItemList.Casing_CuttingFactoryFrame.get(1));
        GTValues.RA.stdBuilder()
            .itemInputs(
                MaterialsAlloy.MARAGING300.getPlate(4),
                MaterialsAlloy.STELLITE.getPlate(2),
                MaterialsAlloy.TALONITE.getFrameBox(1),
                GTUtility.getIntegratedCircuit(1))
            .itemOutputs(GregtechItemList.Casing_CuttingFactoryFrame.get(1L))
            .duration(2 * SECONDS + 10 * TICKS)
            .eut(16)
            .addTo(assemblerRecipes);

        RecipeUtils.addShapedRecipe(
            plate,
            "circuitData",
            plate,
            "wireFinePlatinum",
            IV_MACHINE_Cutter,
            "wireFinePlatinum",
            plate,
            "circuitData",
            plate,
            GregtechItemList.Industrial_CuttingFactoryController.get(1));

        // IV_MACHINE_Extruder
        plate = MaterialsAlloy.INCONEL_690.getPlate(1);
        RecipeUtils.addShapedRecipe(
            plate,
            ToolDictNames.craftingToolHardHammer.name(),
            plate,
            "plateTalonite",
            "frameGtStaballoy",
            "plateTalonite",
            plate,
            ToolDictNames.craftingToolWrench.name(),
            plate,
            GregtechItemList.Casing_Extruder.get(1));
        GTValues.RA.stdBuilder()
            .itemInputs(
                MaterialsAlloy.INCONEL_690.getPlate(4),
                MaterialsAlloy.TALONITE.getPlate(2),
                MaterialsAlloy.STABALLOY.getFrameBox(1),
                GTUtility.getIntegratedCircuit(1))
            .itemOutputs(GregtechItemList.Casing_Extruder.get(1L))
            .duration(2 * SECONDS + 10 * TICKS)
            .eut(16)
            .addTo(assemblerRecipes);

        RecipeUtils.addShapedRecipe(
            plate,
            "circuitElite",
            plate,
            ItemList.Electric_Piston_IV.get(1),
            IV_MACHINE_Extruder,
            ItemList.Electric_Piston_IV.get(1),
            plate,
            "circuitElite",
            plate,
            GregtechItemList.Industrial_Extruder.get(1));

        plate = MaterialsAlloy.AQUATIC_STEEL.getPlate(1);
        RecipeUtils.addShapedRecipe(
            plate,
            ToolDictNames.craftingToolHardHammer.name(),
            plate,
            "plateEglinSteel",
            "frameGtEglinSteel",
            "plateEglinSteel",
            plate,
            ToolDictNames.craftingToolWrench.name(),
            plate,
            GregtechItemList.Casing_FishPond.get(1));
        GTValues.RA.stdBuilder()
            .itemInputs(
                MaterialsAlloy.AQUATIC_STEEL.getPlate(4),
                MaterialsAlloy.EGLIN_STEEL.getPlate(2),
                MaterialsAlloy.EGLIN_STEEL.getFrameBox(1),
                GTUtility.getIntegratedCircuit(1))
            .itemOutputs(GregtechItemList.Casing_FishPond.get(1L))
            .duration(2 * SECONDS + 10 * TICKS)
            .eut(16)
            .addTo(assemblerRecipes);

        RecipeUtils.addShapedRecipe(
            plate,
            "circuitElite",
            plate,
            "wireFineElectrum",
            GregtechItemList.FishTrap.get(1),
            "wireFineElectrum",
            plate,
            "circuitElite",
            plate,
            GregtechItemList.Industrial_FishingPond.get(1));

        // Advanced Vacuum Freezer
        plate = MaterialsAlloy.LEAGRISIUM.getPlateDouble(1);
        ItemStack gear = MaterialsAlloy.INCOLOY_MA956.getGear(1);
        ItemStack frame = MaterialsAlloy.NITINOL_60.getFrameBox(1);
        ItemStack cell1 = ItemList.Reactor_Coolant_He_6.get(1);
        ItemStack cell2 = ItemList.Reactor_Coolant_NaK_6.get(1);

        RecipeUtils.addShapedRecipe(
            plate,
            gear,
            plate,
            cell1,
            frame,
            cell2,
            plate,
            gear,
            plate,
            GregtechItemList.Casing_AdvancedVacuum.get(1));
        RecipeUtils.addShapedRecipe(
            gear,
            "circuitMaster",
            gear,
            ItemList.Electric_Piston_IV.get(1),
            GregtechItemList.Casing_AdvancedVacuum.get(1),
            ItemList.Electric_Piston_IV.get(1),
            plate,
            GregtechItemList.Gregtech_Computer_Cube.get(1),
            plate,
            GregtechItemList.Industrial_Cryogenic_Freezer.get(1));

        // Advanced Blast Furnace
        plate = MaterialsAlloy.HASTELLOY_N.getPlateDouble(1);
        gear = MaterialsAlloy.HASTELLOY_W.getGear(1);
        frame = MaterialsAlloy.HASTELLOY_X.getFrameBox(1);
        cell1 = getModItem(Mods.IndustrialCraft2.ID, "reactorHeatSwitchDiamond", 1, 1);
        cell2 = getModItem(Mods.IndustrialCraft2.ID, "reactorVentGold", 1, 1);
        ItemStack cell3 = getModItem(Mods.IndustrialCraft2.ID, "reactorVentDiamond", 1, 1);

        RecipeUtils.addShapedRecipe(
            plate,
            cell1,
            plate,
            cell3,
            frame,
            cell2,
            plate,
            gear,
            plate,
            GregtechItemList.Casing_Adv_BlastFurnace.get(1));
        RecipeUtils.addShapedRecipe(
            gear,
            "circuitMaster",
            gear,
            ItemList.Robot_Arm_IV.get(1),
            GregtechItemList.Casing_Adv_BlastFurnace.get(1),
            ItemList.Robot_Arm_IV.get(1),
            plate,
            GregtechItemList.Gregtech_Computer_Cube.get(1),
            plate,
            GregtechItemList.Machine_Adv_BlastFurnace.get(1));
        // Hatch_Input_Pyrotheum
        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Hatch_Input_IV.get(1),
                GregtechItemList.Casing_Adv_BlastFurnace.get(1),
                MaterialsAlloy.MARAGING250.getPlate(4),
                MaterialsAlloy.MARAGING300.getGear(1),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.IV, 2),
                GTUtility.getIntegratedCircuit(1))
            .itemOutputs(GregtechItemList.Hatch_Input_Pyrotheum.get(1L))
            .duration(2 * SECONDS + 10 * TICKS)
            .eut(16)
            .addTo(assemblerRecipes);
        // Casing_Adv_BlastFurnace
        GTValues.RA.stdBuilder()
            .itemInputs(
                MaterialsAlloy.HASTELLOY_X.getFrameBox(1),
                MaterialsAlloy.HASTELLOY_N.getPlateDouble(4),
                MaterialsAlloy.HASTELLOY_W.getGear(1),
                getModItem(Mods.IndustrialCraft2.ID, "reactorHeatSwitchDiamond", 1, 1),
                getModItem(Mods.IndustrialCraft2.ID, "reactorVentGold", 1, 1),
                getModItem(Mods.IndustrialCraft2.ID, "reactorVentDiamond", 1, 1),
                GTUtility.getIntegratedCircuit(1))
            .itemOutputs(GregtechItemList.Casing_Adv_BlastFurnace.get(1L))
            .duration(2 * SECONDS + 10 * TICKS)
            .eut(16)
            .addTo(assemblerRecipes);

        // Advanced Implosion Compressor
        plate = ItemUtils.getItemStackOfAmountFromOreDict("plateAlloyIridium", 1);
        gear = MaterialsAlloy.LEAGRISIUM.getGear(1);
        frame = MaterialsAlloy.CINOBITE.getFrameBox(1);
        cell1 = getModItem(Mods.IndustrialCraft2.ID, "reactorHeatSwitchDiamond", 1, 1);
        cell2 = getModItem(Mods.IndustrialCraft2.ID, "reactorVentGold", 1, 1);

        RecipeUtils.addShapedRecipe(
            gear,
            "circuitMaster",
            gear,
            ItemList.Field_Generator_IV.get(1),
            ItemList.Hull_ZPM.get(1),
            ItemList.Robot_Arm_IV.get(1),
            plate,
            GregtechItemList.Gregtech_Computer_Cube.get(1),
            plate,
            GregtechItemList.Machine_Adv_ImplosionCompressor.get(1));

        // Supply Depot
        plate = MaterialsAlloy.HASTELLOY_C276.getPlateDouble(1);
        frame = MaterialsAlloy.TUNGSTEN_CARBIDE.getFrameBox(1);
        cell1 = ItemList.Conveyor_Module_HV.get(1);
        cell2 = ItemList.Electric_Motor_HV.get(1);
        ItemStack casingAmazon = GregtechItemList.Casing_AmazonWarehouse.get(1);

        RecipeUtils.addShapedRecipe(
            plate,
            cell2,
            plate,
            ToolDictNames.craftingToolWrench.name(),
            frame,
            ToolDictNames.craftingToolHardHammer.name(),
            plate,
            cell1,
            plate,
            GregtechItemList.Casing_AmazonWarehouse.get(1));
        GTValues.RA.stdBuilder()
            .itemInputs(
                MaterialsAlloy.TUNGSTEN_CARBIDE.getFrameBox(1),
                MaterialsAlloy.HASTELLOY_C276.getPlateDouble(4),
                ItemList.Electric_Motor_HV.get(1),
                ItemList.Conveyor_Module_HV.get(1))
            .itemOutputs(GregtechItemList.Casing_AmazonWarehouse.get(1L))
            .duration(2 * SECONDS + 10 * TICKS)
            .eut(16)
            .addTo(assemblerRecipes);

        RecipeUtils.addShapedRecipe(
            casingAmazon,
            "circuitElite",
            casingAmazon,
            ItemList.Electric_Piston_IV.get(1),
            ItemList.Machine_IV_Boxinator.get(1),
            ItemList.Electric_Piston_IV.get(1),
            ItemList.Conveyor_Module_IV.get(1),
            casingAmazon,
            ItemList.Conveyor_Module_IV.get(1),
            GregtechItemList.Amazon_Warehouse_Controller.get(1));

        // Industrial Mixing Machine
        RecipeUtils.addShapedRecipe(
            "plateStaballoy",
            "circuitElite",
            "plateStaballoy",
            "plateZirconiumCarbide",
            IV_MACHINE_Mixer,
            "plateZirconiumCarbide",
            "plateStaballoy",
            "circuitElite",
            "plateStaballoy",
            GregtechItemList.Industrial_Mixer.get(1));

        final ItemStack staballoyPlate = MaterialsAlloy.STABALLOY.getPlate(1);

        RecipeUtils.addShapedRecipe(
            staballoyPlate,
            ToolDictNames.craftingToolHardHammer.name(),
            staballoyPlate,
            "plateStainlessSteel",
            "frameGtZirconiumCarbide",
            "plateStainlessSteel",
            staballoyPlate,
            ToolDictNames.craftingToolWrench.name(),
            staballoyPlate,
            GregtechItemList.Casing_Multi_Use.get(1));

        GTValues.RA.stdBuilder()
            .itemInputs(
                MaterialsAlloy.STABALLOY.getPlate(4),
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 2),
                MaterialsAlloy.ZIRCONIUM_CARBIDE.getFrameBox(1),
                GTUtility.getIntegratedCircuit(1))
            .itemOutputs(GregtechItemList.Casing_Multi_Use.get(1L))
            .duration(2 * SECONDS + 10 * TICKS)
            .eut(16)
            .addTo(assemblerRecipes);

        // Drilling Platform Casings
        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemUtils.getItemStackOfAmountFromOreDict("frameGtTriniumNaquadahCarbonite", 4),
                ItemUtils.getItemStackOfAmountFromOreDict("plateDoubleTriniumTitaniumAlloy", 1 * (1)),
                ItemUtils.getItemStackOfAmountFromOreDict("gearGtPikyonium64B", 2 * (1)),
                MaterialsAlloy.TRINIUM_REINFORCED_STEEL.getPlateDouble(4 * 1),
                ItemList.Hull_LuV.get(1))
            .itemOutputs(GregtechItemList.Casing_BedrockMiner.get(1))
            .fluidInputs(MaterialsAlloy.MARAGING350.getFluidStack(16 * INGOTS))
            .duration((int) GTValues.V[4])
            .eut(TierEU.RECIPE_LuV)
            .addTo(assemblerRecipes);

        // Reservoir Hatch
        if (RemoteIO.isModLoaded()) {
            GTValues.RA.stdBuilder()
                .itemInputs(
                    ItemList.Hatch_Input_EV.get(1),
                    GTModHandler.getModItem(RemoteIO.ID, "tile.machine", 1),
                    ItemList.Electric_Pump_EV.get(1))
                .itemOutputs(GregtechItemList.Hatch_Reservoir.get(1))
                .duration(5 * SECONDS)
                .eut(TierEU.RECIPE_EV)
                .addTo(assemblerRecipes);
        }
        // Mystic Frame
        int aCostMultiplier = 1;
        GTValues.RA.stdBuilder()
            .itemInputs(
                GregtechItemList.Casing_Multi_Use.get(1),
                ItemList.Field_Generator_MV.get(1),
                ItemList.Field_Generator_HV.get(1),
                ItemList.Emitter_HV.get(1),
                ItemList.Sensor_HV.get(1),
                CI.getTieredComponent(OrePrefixes.plate, 7, 8 * aCostMultiplier),
                CI.getTieredComponent(OrePrefixes.wireGt08, 8, 4 * aCostMultiplier))
            .itemOutputs(new ItemStack(DimensionEverglades.blockPortalFrame, 2))
            .fluidInputs(CI.getTieredFluid(6, (8 * INGOTS)))
            .duration(4 * MINUTES + 30 * SECONDS)
            .eut(TierEU.RECIPE_LuV)
            .addTo(assemblerRecipes);

        Logger.INFO("Done loading recipes for the Various machine blocks.");
    }

    private static void energyCores() {

        ItemStack[] aBufferOutput = new ItemStack[] { RECIPE_Buffer_ULV, RECIPE_Buffer_LV, RECIPE_Buffer_MV,
            RECIPE_Buffer_HV, RECIPE_Buffer_EV, RECIPE_Buffer_IV, RECIPE_Buffer_LuV, RECIPE_Buffer_ZPM,
            RECIPE_Buffer_UV, RECIPE_Buffer_MAX };

        long[] voltageTiers = new long[] { 16, TierEU.RECIPE_LV, TierEU.RECIPE_MV, TierEU.RECIPE_HV, TierEU.RECIPE_EV,
            TierEU.RECIPE_IV, TierEU.RECIPE_LuV, TierEU.RECIPE_ZPM, TierEU.RECIPE_UV, TierEU.RECIPE_UHV };

        ItemStack[] aOutput = new ItemStack[] { GregtechItemList.Energy_Core_ULV.get(1),
            GregtechItemList.Energy_Core_LV.get(1), GregtechItemList.Energy_Core_MV.get(1),
            GregtechItemList.Energy_Core_HV.get(1), GregtechItemList.Energy_Core_EV.get(1),
            GregtechItemList.Energy_Core_IV.get(1), GregtechItemList.Energy_Core_LuV.get(1),
            GregtechItemList.Energy_Core_ZPM.get(1), GregtechItemList.Energy_Core_UV.get(1),
            GregtechItemList.Energy_Core_UHV.get(1), };

        for (int i = 0; i < 10; i++) {

            ItemStack aPrevTier = (i == 0 ? CI.getTieredMachineHull(1) : aOutput[i - 1]);
            aPrevTier.stackSize = 1;
            int aTier = (i + 1);
            GTValues.RA.stdBuilder()
                .itemInputs(
                    aPrevTier,
                    CI.getTieredComponent(OrePrefixes.plate, aTier, 4),
                    CI.getTieredComponent(OrePrefixes.cableGt04, i, 2),
                    CI.getTieredComponent(OrePrefixes.circuit, aTier, 2),
                    CI.getTieredComponent(OrePrefixes.screw, aTier, 6),
                    CI.getTieredComponent(OrePrefixes.bolt, i, 12))
                .itemOutputs(aOutput[i])
                .fluidInputs(CI.getTieredFluid(i, (aTier * 4 * INGOTS)))
                .duration(45 * 10 * 1 * (aTier))
                .eut(voltageTiers[i])
                .addTo(assemblerRecipes);
            // Energy Buffer
            GTValues.RA.stdBuilder()
                .itemInputs(
                    GTUtility.copyAmount(4, aOutput[i]),
                    CI.getTieredComponent(OrePrefixes.plate, aTier, 8),
                    CI.getTieredComponent(OrePrefixes.wireGt08, i, 4),
                    CI.getTieredComponent(OrePrefixes.circuit, i, 4),
                    CI.getTieredComponent(OrePrefixes.stickLong, aTier, 4),
                    CI.getTieredComponent(OrePrefixes.gearGt, i, 5))
                .itemOutputs(aBufferOutput[i])
                .fluidInputs(CI.getTieredFluid(aTier, (16 * INGOTS * aTier)))
                .duration(45 * 20 * 1 * (aTier))
                .eut(voltageTiers[i])
                .addTo(assemblerRecipes);

        }
    }

    private static void wirelessChargers() {

        ItemStack[] aChargers = new ItemStack[] { GregtechItemList.Charger_LV.get(1),
            GregtechItemList.Charger_MV.get(1), GregtechItemList.Charger_HV.get(1), GregtechItemList.Charger_EV.get(1),
            GregtechItemList.Charger_IV.get(1), GregtechItemList.Charger_LuV.get(1),
            GregtechItemList.Charger_ZPM.get(1), GregtechItemList.Charger_UV.get(1),
            GregtechItemList.Charger_UHV.get(1) };

        long[] voltageTiers = new long[] { 16, TierEU.RECIPE_LV, TierEU.RECIPE_MV, TierEU.RECIPE_HV, TierEU.RECIPE_EV,
            TierEU.RECIPE_IV, TierEU.RECIPE_LuV, TierEU.RECIPE_ZPM, TierEU.RECIPE_UV, TierEU.RECIPE_UHV };

        for (int tier = 1; tier < aChargers.length + 1; tier++) {

            ItemStack[] aInputs = new ItemStack[] { CI.getTieredMachineHull(tier, 1),
                CI.getTransmissionComponent(tier, 2), CI.getFieldGenerator(tier, 1),
                CI.getTieredComponent(OrePrefixes.plate, tier + 1, 4),
                CI.getTieredComponent(OrePrefixes.circuit, tier + 1, 2), };
            GTValues.RA.stdBuilder()
                .itemInputs(aInputs)
                .itemOutputs(aChargers[tier - 1])
                .fluidInputs(CI.getAlternativeTieredFluid(tier, (2 * INGOTS * (tier + 1))))
                .duration(45 * 10 * (tier + 1))
                .eut(voltageTiers[tier])
                .addTo(assemblerRecipes);

        }
    }

    private static void largeArcFurnace() {
        int aCostMultiplier = 1;
        GTValues.RA.stdBuilder()
            .itemInputs(
                GregtechItemList.Casing_Multi_Use.get(aCostMultiplier),
                CI.getTransmissionComponent(2, 2 * aCostMultiplier),
                CI.getElectricPiston(4, 2 * aCostMultiplier),
                CI.getTieredComponent(OrePrefixes.plate, 5, 4 * aCostMultiplier),
                CI.getTieredComponent(OrePrefixes.pipeSmall, 4, 1 * aCostMultiplier))
            .itemOutputs(GregtechItemList.Casing_Industrial_Arc_Furnace.get(1))
            .fluidInputs(CI.getAlternativeTieredFluid(5, 8 * INGOTS))
            .duration(60 * SECONDS)
            .eut(TierEU.RECIPE_IV)
            .addTo(assemblerRecipes);
        GTValues.RA.stdBuilder()
            .itemInputs(
                GregtechItemList.Casing_Industrial_Arc_Furnace.get(1),
                CI.getFieldGenerator(4, 2 * aCostMultiplier),
                CI.getRobotArm(5, 4 * aCostMultiplier),
                CI.getEnergyCore(4, 2 * aCostMultiplier),
                CI.getTieredComponent(OrePrefixes.plate, 6, 8 * aCostMultiplier),
                CI.getTieredComponent(OrePrefixes.circuit, 5, 8 * aCostMultiplier))
            .itemOutputs(GregtechItemList.Industrial_Arc_Furnace.get(1))
            .fluidInputs(CI.getAlternativeTieredFluid(6, 20 * INGOTS))
            .duration(8 * MINUTES)
            .eut(TierEU.RECIPE_LuV)
            .addTo(assemblerRecipes);

    }

    private static void industrialVacuumFurnace() {

        GTValues.RA.stdBuilder()
            .itemInputs(
                GregtechItemList.Casing_Multi_Use.get(1),
                new ItemStack(GregTechAPI.sBlockCasings5, 1, 2),
                CI.getElectricPiston(3, 2),
                CI.getTieredComponent(OrePrefixes.plate, 6, 4),
                CI.getTieredComponent(OrePrefixes.gearGt, 6, 2))
            .itemOutputs(GregtechItemList.Casing_Vacuum_Furnace.get(1))
            .fluidInputs(CI.getTertiaryTieredFluid(5, 8 * INGOTS))
            .duration(60 * SECONDS)
            .eut(TierEU.RECIPE_LuV)
            .addTo(assemblerRecipes);
        GTValues.RA.stdBuilder()
            .itemInputs(
                GregtechItemList.Casing_Vacuum_Furnace.get(1),
                CI.getTieredComponent(OrePrefixes.wireGt16, 7, 4),
                CI.getRobotArm(4, 4),
                CI.getTieredComponent(OrePrefixes.plate, 6, 8),
                CI.getTieredComponent(OrePrefixes.circuit, 6, 8))
            .itemOutputs(GregtechItemList.Controller_Vacuum_Furnace.get(1))
            .fluidInputs(CI.getTieredFluid(6, 20 * INGOTS))
            .duration(12 * MINUTES)
            .eut(TierEU.RECIPE_LuV)
            .addTo(assemblerRecipes);

    }

    private static void milling() {

        // Isa Mill Controller
        GTValues.RA.stdBuilder()
            .metadata(RESEARCH_ITEM, ItemList.Machine_IV_Macerator.get(1))
            .metadata(SCANNING, new Scanning(40 * SECONDS, TierEU.RECIPE_IV))
            .itemInputs(
                GregtechItemList.Casing_IsaMill_Casing.get(4),
                GregtechItemList.Casing_IsaMill_Gearbox.get(4),
                ItemList.Component_Grinder_Tungsten.get(16),
                new Object[] { "circuitMaster", 16 },
                MaterialsAlloy.INCONEL_625.getGear(8),
                MaterialsAlloy.INCONEL_625.getPlate(32),
                MaterialsAlloy.ZERON_100.getPlateDouble(16),
                MaterialsAlloy.ZERON_100.getScrew(64))
            .fluidInputs(
                CI.getTieredFluid(6, 16 * INGOTS),
                CI.getAlternativeTieredFluid(6, 32 * INGOTS),
                CI.getTertiaryTieredFluid(6, 32 * INGOTS))
            .itemOutputs(GregtechItemList.Controller_IsaMill.get(1))
            .eut(TierEU.RECIPE_LuV)
            .duration(10 * MINUTES)
            .addTo(AssemblyLine);

        // Isa Mill Gearbox
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(7),
                ItemList.Casing_Gearbox_Titanium.get(2),
                MaterialsAlloy.INCONEL_625.getGear(4),
                MaterialsAlloy.INCONEL_625.getPlate(16))
            .itemOutputs(GregtechItemList.Casing_IsaMill_Gearbox.get(1))
            .fluidInputs(MaterialsAlloy.TUNGSTENSTEEL.getFluidStack(8 * INGOTS))
            .duration(2 * MINUTES)
            .eut(TierEU.RECIPE_LuV)
            .addTo(assemblerRecipes);
        // Isa Mill Casing
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(7),
                GregtechItemList.Casing_MacerationStack.get(1),
                MaterialsAlloy.ZERON_100.getPlateDouble(2),
                MaterialsAlloy.ZERON_100.getRod(4),
                MaterialsAlloy.ZERON_100.getScrew(8))
            .itemOutputs(GregtechItemList.Casing_IsaMill_Casing.get(1))
            .fluidInputs(MaterialsElements.getInstance().TITANIUM.getFluidStack(4 * INGOTS))
            .duration(2 * MINUTES)
            .eut(TierEU.RECIPE_LuV)
            .addTo(assemblerRecipes);
        // Isa Mill Pipe
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(7),
                GregtechItemList.Casing_IsaMill_Casing.get(1),
                ItemList.Casing_Item_Pipe_Quantium.get(1),
                CI.getTieredComponentOfMaterial(Materials.HSSE, OrePrefixes.ring, 8),
                CI.getTieredComponentOfMaterial(Materials.HSSE, OrePrefixes.plate, 8),
                CI.getTieredComponentOfMaterial(Materials.HSSE, OrePrefixes.screw, 8))
            .itemOutputs(GregtechItemList.Casing_IsaMill_Pipe.get(1))
            .fluidInputs(MaterialsElements.getInstance().ALUMINIUM.getFluidStack(8 * INGOTS))
            .duration(8 * MINUTES)
            .eut(TierEU.RECIPE_EV)
            .addTo(assemblerRecipes);

        // Flotation Cell Controller
        GTValues.RA.stdBuilder()
            .metadata(RESEARCH_ITEM, ItemList.Distillation_Tower.get(1))
            .metadata(SCANNING, new Scanning(40 * SECONDS, TierEU.RECIPE_IV))
            .itemInputs(
                GregtechItemList.Machine_Adv_DistillationTower.get(2),
                GregtechItemList.Casing_Extruder.get(4L),
                GregtechItemList.Casing_Flotation_Cell.get(4),
                ItemList.Electric_Pump_LuV.get(4),
                MaterialsAlloy.STELLITE.getGear(8),
                MaterialsAlloy.STELLITE.getPlate(32),
                MaterialsAlloy.HASTELLOY_N.getPlateDouble(16),
                MaterialsAlloy.HASTELLOY_N.getScrew(64))
            .fluidInputs(
                CI.getTieredFluid(5, 16 * INGOTS),
                CI.getAlternativeTieredFluid(4, 32 * INGOTS),
                CI.getTertiaryTieredFluid(4, 32 * INGOTS))
            .itemOutputs(GregtechItemList.Controller_Flotation_Cell.get(1))
            .eut(TierEU.RECIPE_LuV)
            .duration(60 * SECONDS)
            .addTo(AssemblyLine);

        // Flotation Cell Casing
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(7),
                GregtechItemList.Casing_Extruder.get(4L),
                CI.getTieredComponentOfMaterial(Materials.HSSG, OrePrefixes.plateDouble, 4),
                MaterialsAlloy.AQUATIC_STEEL.getPlate(8),
                MaterialsAlloy.AQUATIC_STEEL.getRing(8),
                MaterialsAlloy.AQUATIC_STEEL.getRotor(4))
            .itemOutputs(GregtechItemList.Casing_Flotation_Cell.get(1))
            .fluidInputs(MaterialsAlloy.STAINLESS_STEEL.getFluidStack(8 * INGOTS))
            .duration(2 * MINUTES)
            .eut(TierEU.RECIPE_LuV)
            .addTo(assemblerRecipes);
        // Milling Bus
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(7),
                CI.getTieredGTPPMachineCasing(5, 1),
                ItemList.Hatch_Input_Bus_EV.get(1),
                CI.getTieredComponentOfMaterial(Materials.Titanium, OrePrefixes.gearGt, 8),
                CI.getTieredComponentOfMaterial(Materials.TungstenSteel, OrePrefixes.plate, 32),
                CI.getTieredComponentOfMaterial(Materials.SolderingAlloy, OrePrefixes.wireFine, 16))
            .itemOutputs(GregtechItemList.Bus_Milling_Balls.get(1))
            .fluidInputs(MaterialsElements.getInstance().TUNGSTEN.getFluidStack(8 * INGOTS))
            .duration(4 * MINUTES)
            .eut(TierEU.RECIPE_IV)
            .addTo(assemblerRecipes);

    }

    private static void sparging() {

        // Sparge Tower Research
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(8),
                MaterialsElements.getInstance().HELIUM.getCell(8),
                MaterialsElements.getInstance().FLUORINE.getCell(8),
                MaterialsAlloy.HS188A.getIngot(8),
                ItemList.Distillation_Tower.get(1))
            .itemOutputs(ItemDummyResearch.getResearchStack(ASSEMBLY_LINE_RESEARCH.RESEARCH_10_SPARGING, 1))
            .duration(5 * MINUTES)
            .eut(TierEU.RECIPE_IV)
            .addTo(assemblerRecipes);

        // Sparge Tower Controller
        GTValues.RA.stdBuilder()
            .metadata(RESEARCH_ITEM, ItemDummyResearch.getResearchStack(ASSEMBLY_LINE_RESEARCH.RESEARCH_10_SPARGING, 1))
            .metadata(SCANNING, new Scanning(1 * MINUTES + 20 * SECONDS, TierEU.RECIPE_IV))
            .itemInputs(
                GregtechItemList.Casing_Sparge_Tower_Exterior.get(4),
                CI.getTieredGTPPMachineCasing(4, 4),
                ItemList.Machine_IV_Distillery.get(1),
                new Object[] { "circuitElite", 8 },
                MaterialsAlloy.HS188A.getGear(8),
                MaterialsAlloy.HS188A.getPlate(32),
                MaterialsAlloy.HASTELLOY_N.getPlateDouble(16),
                MaterialsAlloy.HASTELLOY_N.getScrew(64),
                CI.getTieredComponentOfMaterial(Materials.YttriumBariumCuprate, OrePrefixes.wireFine, 64),
                CI.getTieredComponentOfMaterial(Materials.YttriumBariumCuprate, OrePrefixes.wireFine, 64),
                CI.getTieredComponentOfMaterial(Materials.Platinum, OrePrefixes.foil, 64))
            .fluidInputs(
                CI.getTieredFluid(4, 16 * INGOTS),
                CI.getAlternativeTieredFluid(3, 32 * INGOTS),
                CI.getTertiaryTieredFluid(3, 32 * INGOTS))
            .itemOutputs(GregtechItemList.Controller_Sparge_Tower.get(1))
            .eut(TierEU.RECIPE_LuV)
            .duration(60 * SECONDS)
            .addTo(AssemblyLine);

        // Sparge Tower Casing
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(8),
                CI.getTieredGTPPMachineCasing(3, 1),
                MaterialsAlloy.HS188A.getPlate(2),
                MaterialsAlloy.HASTELLOY_N.getRing(4),
                CI.getTieredComponentOfMaterial(Materials.TungstenSteel, OrePrefixes.plateDouble, 4),
                MaterialsAlloy.HASTELLOY_N.getScrew(4))
            .itemOutputs(GregtechItemList.Casing_Sparge_Tower_Exterior.get(1))
            .fluidInputs(MaterialsAlloy.STAINLESS_STEEL.getFluidStack(8 * INGOTS))
            .duration(2 * MINUTES)
            .eut(TierEU.RECIPE_IV)
            .addTo(assemblerRecipes);

    }

    private static void chisels() {
        ItemStack[] aChisels = new ItemStack[] { GregtechItemList.GT_Chisel_LV.get(1),
            GregtechItemList.GT_Chisel_MV.get(1), GregtechItemList.GT_Chisel_HV.get(1), };

        long[] voltageTiers = new long[] { TierEU.RECIPE_LV, TierEU.RECIPE_MV, TierEU.RECIPE_HV };
        for (int i = 1; i < 4; i++) {
            GTValues.RA.stdBuilder()
                .itemInputs(
                    GTUtility.getIntegratedCircuit(10 + i),
                    CI.getTieredMachineCasing(i),
                    CI.getPlate(i, 4),
                    CI.getElectricMotor(i, 2),
                    CI.getConveyor(i, 2),
                    CI.getRobotArm(i, 1))
                .itemOutputs(aChisels[i - 1])
                .fluidInputs(CI.getTieredFluid(i, 4 * INGOTS))
                .duration(20 * SECONDS)
                .eut(voltageTiers[i - 1])
                .addTo(assemblerRecipes);

        }

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(14),
                aChisels[2],
                CI.getPlate(4, 8),
                CI.getElectricMotor(4, 8),
                CI.getConveyor(4, 8),
                CI.getRobotArm(4, 4))
            .itemOutputs(GregtechItemList.Controller_IndustrialAutoChisel.get(1))
            .fluidInputs(CI.getTieredFluid(4, 8 * INGOTS))
            .duration(20 * SECONDS)
            .eut(TierEU.RECIPE_EV)
            .addTo(assemblerRecipes);
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(14),
                ItemList.Casing_SolidSteel.get(2),
                CI.getPlate(4, 2),
                CI.getTieredComponent(OrePrefixes.plate, 3, 4),
                CI.getTieredComponent(OrePrefixes.ring, 3, 8),
                CI.getTieredComponent(OrePrefixes.rod, 2, 4))
            .itemOutputs(GregtechItemList.Casing_IndustrialAutoChisel.get(1))
            .fluidInputs(CI.getTieredFluid(2, 2 * INGOTS))
            .duration(20 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(assemblerRecipes);

    }

    private static void rockBreaker() {

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(12),
                ItemList.Machine_EV_RockBreaker.get(1),
                MaterialsAlloy.STAINLESS_STEEL.getPlate(8),
                MaterialsAlloy.STAINLESS_STEEL.getRing(4),
                CI.getTieredComponentOfMaterial(Materials.Aluminium, OrePrefixes.plateDouble, 8),
                MaterialsAlloy.EGLIN_STEEL.getScrew(8))
            .itemOutputs(GregtechItemList.Controller_IndustrialRockBreaker.get(1))
            .fluidInputs(MaterialsElements.getInstance().ALUMINIUM.getFluidStack(8 * INGOTS))
            .duration(2 * MINUTES)
            .eut(TierEU.RECIPE_EV)
            .addTo(assemblerRecipes);

    }

    private static void fakeMachineCasingCovers() {
        int aMaxTier = GTValues.VOLTAGE_NAMES.length;
        ItemStack[] aTier = new ItemStack[aMaxTier];
        for (int i = 0; i < aMaxTier; i++) {
            aTier[i] = new ItemStack(CoverManager.Cover_Gt_Machine_Casing, 7, i);
        }
        // Add recipes for new ones
        for (int i = 0; i < aMaxTier - 1; i++) {
            GTValues.RA.stdBuilder()
                .itemInputs(CI.getTieredMachineCasing(i), GTUtility.getIntegratedCircuit(i))
                .itemOutputs(aTier[i])
                .duration(i * 5 * SECONDS)
                .eut(GTValues.VP[i])
                .addTo(cutterRecipes);
        }
    }

    private static void superBuses() {
        GregtechItemList[] mSuperBusesInput = new GregtechItemList[] { GregtechItemList.Hatch_SuperBus_Input_LV,
            GregtechItemList.Hatch_SuperBus_Input_MV, GregtechItemList.Hatch_SuperBus_Input_HV,
            GregtechItemList.Hatch_SuperBus_Input_EV, GregtechItemList.Hatch_SuperBus_Input_IV,
            GregtechItemList.Hatch_SuperBus_Input_LuV, GregtechItemList.Hatch_SuperBus_Input_ZPM,
            GregtechItemList.Hatch_SuperBus_Input_UV, GregtechItemList.Hatch_SuperBus_Input_MAX, };

        GregtechItemList[] mSuperBusesOutput = new GregtechItemList[] { GregtechItemList.Hatch_SuperBus_Output_LV,
            GregtechItemList.Hatch_SuperBus_Output_MV, GregtechItemList.Hatch_SuperBus_Output_HV,
            GregtechItemList.Hatch_SuperBus_Output_EV, GregtechItemList.Hatch_SuperBus_Output_IV,
            GregtechItemList.Hatch_SuperBus_Output_LuV, GregtechItemList.Hatch_SuperBus_Output_ZPM,
            GregtechItemList.Hatch_SuperBus_Output_UV, GregtechItemList.Hatch_SuperBus_Output_MAX, };

        ItemStack[] mInputHatch = new ItemStack[] { ItemList.Hatch_Input_Bus_EV.get(1),
            ItemList.Hatch_Input_Bus_IV.get(1), ItemList.Hatch_Input_Bus_LuV.get(1),
            ItemList.Hatch_Input_Bus_ZPM.get(1), ItemList.Hatch_Input_Bus_UV.get(1),
            ItemList.Hatch_Input_Bus_MAX.get(1), GregtechItemList.Hatch_SuperBus_Input_LV.get(1),
            GregtechItemList.Hatch_SuperBus_Input_MV.get(1), GregtechItemList.Hatch_SuperBus_Input_HV.get(1),
            GregtechItemList.Hatch_SuperBus_Input_EV.get(1), };

        ItemStack[] mOutputHatch = new ItemStack[] { ItemList.Hatch_Output_Bus_EV.get(1),
            ItemList.Hatch_Output_Bus_IV.get(1), ItemList.Hatch_Output_Bus_LuV.get(1),
            ItemList.Hatch_Output_Bus_ZPM.get(1), ItemList.Hatch_Output_Bus_UV.get(1),
            ItemList.Hatch_Output_Bus_MAX.get(1), GregtechItemList.Hatch_SuperBus_Output_LV.get(1),
            GregtechItemList.Hatch_SuperBus_Output_MV.get(1), GregtechItemList.Hatch_SuperBus_Output_HV.get(1),
            GregtechItemList.Hatch_SuperBus_Output_EV.get(1), };

        // Input Buses
        for (int tier = 1; tier < mSuperBusesInput.length + 1; tier++) {
            GTValues.RA.stdBuilder()
                .itemInputs(
                    GTUtility.getIntegratedCircuit(17),
                    mInputHatch[tier - 1],
                    CI.getElectricMotor(tier, 2),
                    CI.getConveyor(tier, 5),
                    CI.getBolt(tier, 16),
                    CI.getTieredComponent(OrePrefixes.circuit, tier, 2))
                .itemOutputs(mSuperBusesInput[tier - 1].get(1))
                .fluidInputs(CI.getAlternativeTieredFluid(tier, 8 * INGOTS))
                .duration(60 * SECONDS)
                .eut(GTValues.VP[tier])
                .addTo(assemblerRecipes);

        }
        // Output Buses
        for (int tier = 1; tier < mSuperBusesOutput.length + 1; tier++) {
            GTValues.RA.stdBuilder()
                .itemInputs(
                    GTUtility.getIntegratedCircuit(18),
                    mOutputHatch[tier - 1],
                    CI.getElectricPiston(tier, 2),
                    CI.getConveyor(tier, 5),
                    CI.getGear(tier, 3),
                    CI.getTieredComponent(OrePrefixes.circuit, tier, 2))
                .itemOutputs(mSuperBusesOutput[tier - 1].get(1))
                .fluidInputs(CI.getTertiaryTieredFluid(tier, 8 * INGOTS))
                .duration(60 * SECONDS)
                .eut(GTValues.VP[tier])
                .addTo(assemblerRecipes);

        }
    }

    private static void chiselBuses() {
        ItemStack[] mSuperBusesInput = new ItemStack[] { GregtechItemList.Hatch_SuperBus_Input_LV.get(1),
            GregtechItemList.Hatch_SuperBus_Input_MV.get(1), GregtechItemList.Hatch_SuperBus_Input_HV.get(1), };

        ItemStack[] mChiselBuses = new ItemStack[] { GregtechItemList.ChiselBus_LV.get(1),
            GregtechItemList.ChiselBus_MV.get(1), GregtechItemList.ChiselBus_HV.get(1), };

        for (int tier = 1; tier < mChiselBuses.length + 1; tier++) {
            GTValues.RA.stdBuilder()
                .itemInputs(
                    GTUtility.getIntegratedCircuit(17),
                    mSuperBusesInput[tier - 1],
                    CI.getSensor(tier, 1),
                    CI.getRobotArm(tier, 2),
                    CI.getBolt(tier, 16),
                    new ItemStack(Blocks.chest))
                .itemOutputs(mChiselBuses[tier - 1])
                .fluidInputs(CI.getAlternativeTieredFluid(tier, 2 * INGOTS))
                .duration(60 * SECONDS)
                .eut(GTValues.VP[tier + 1])
                .addTo(assemblerRecipes);

        }
    }

    private static void solidifierHatches() {
        ItemStack[] mSuperBusesInput = new ItemStack[] { ItemList.Hatch_Input_IV.get(1),
            ItemList.Hatch_Input_LuV.get(1), ItemList.Hatch_Input_ZPM.get(1), ItemList.Hatch_Input_UV.get(1), };

        ItemStack[] mSolidifierHatches = new ItemStack[] { GregtechItemList.Hatch_Solidifier_I.get(1),
            GregtechItemList.Hatch_Solidifier_II.get(1), GregtechItemList.Hatch_Solidifier_III.get(1),
            GregtechItemList.Hatch_Solidifier_IV.get(1), };

        for (int i = 0; i < 4; i++) {
            int componentTier = i + 5;
            GTValues.RA.stdBuilder()
                .itemInputs(
                    GTUtility.getIntegratedCircuit(17),
                    mSuperBusesInput[i],
                    CI.getSensor(componentTier, 1),
                    CI.getFluidRegulator(componentTier, 1),
                    CI.getTieredComponent(OrePrefixes.circuit, componentTier + 1, 4),
                    new ItemStack(Blocks.chest))
                .itemOutputs(mSolidifierHatches[i])
                .fluidInputs(CI.getTieredFluid(componentTier, 2 * INGOTS))
                .duration(30 * SECONDS)
                .eut(GTValues.VP[componentTier])
                .addTo(assemblerRecipes);

        }
    }
}

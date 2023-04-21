use PortfolioProject

select *
from PortfolioProject.dbo.NashvilleHousing
/*Clearing  data*/

--Standardize date formate--------------------------------------------------------------------------------------------------------------
select SaleDate
from PortfolioProject.dbo.NashvilleHousing

select SaleDate,CONVERT(Date,SaleDate)
from PortfolioProject.dbo.NashvilleHousing

update NashvilleHousing



set SaleDate=Convert(Date,SaleDate)

alter table NashvilleHousing
add saleDateConverted Date;

update NashvilleHousing
set SaleDateConverted=Convert(Date,SaleDate)

select SaleDateConverted,CONVERT(Date,SaleDate)
from PortfolioProject.dbo.NashvilleHousing

/*populate property address data*/----------------------------------------------------------------------------------------------------------

select *
from PortfolioProject.dbo.NashvilleHousing
--where PropertyAddress is null
order by ParcelID

select a.ParcelID,a.PropertyAddress,b.ParcelID,b.PropertyAddress
from PortfolioProject.dbo.NashvilleHousing a
join PortfolioProject.dbo.NashvilleHousing b 
on a.ParcelID =b.ParcelID 
and a.[UniqueID ]!=b.[UniqueID ]


update a
set PropertyAddress=ISNULL(a.PropertyAddress,b.PropertyAddress)
from PortfolioProject.dbo.NashvilleHousing a
join PortfolioProject.dbo.NashvilleHousing b 
on a.ParcelID =b.ParcelID 
and a.[UniqueID ]!=b.[UniqueID ]
where a.PropertyAddress is null

/*Breaking out address into individual columns*/---------------------------------------------------------------------------------------------


select *
from PortfolioProject.dbo.NashvilleHousing
--where PropertyAddress is null
--order by ParcelID

select 
SUBSTRING(PropertyAddress,1,CHARINDEX(',',PropertyAddress)-1) as Address
, SUBSTRING(PropertyAddress,CHARINDEX(',',PropertyAddress)+1,LEN(PropertyAddress)+1) as Address
from PortfolioProject.dbo.NashvilleHousing

alter table NashvilleHousing
add PropertySplitAddress nvarchar(255)

update NashvilleHousing
set PropertySplitAddress=SUBSTRING(PropertyAddress,1,CHARINDEX(',',PropertyAddress)-1) 

alter table NashvilleHousing
add PropertySplitCity nvarchar(255)

update NashvilleHousing
set PropertySplitCity=SUBSTRING(PropertyAddress,CHARINDEX(',',PropertyAddress)+1,LEN(PropertyAddress)+1)

/*Breaking out owneraddress into individual columns*/-----------------------------------------------------------------------------------------

select OwnerAddress
from PortfolioProject.dbo.NashvilleHousing

select
PARSENAME(REPLACE(OwnerAddress,',','.'),3)
,PARSENAME(REPLACE(OwnerAddress,',','.'),2)
,PARSENAME(REPLACE(OwnerAddress,',','.'),1)
from PortfolioProject.dbo.NashvilleHousing

alter table NashvilleHousing
add OwnerSplitAddress nvarchar(255)

update NashvilleHousing
set OwnerSplitAddress=PARSENAME(REPLACE(OwnerAddress,',','.'),3)

alter table NashvilleHousing
add OwnerSplitCity nvarchar(255)

update NashvilleHousing
set OwnerSplitCity=PARSENAME(REPLACE(OwnerAddress,',','.'),2)

alter table NashvilleHousing
add OwnerSplitState nvarchar(255)

update NashvilleHousing
set OwnerSplitState =PARSENAME(REPLACE(OwnerAddress,',','.'),1)


select *
from PortfolioProject.dbo.NashvilleHousing

/*Change Y and N to Yes and No in "SloidAsVacant" field*/-----------------------------------------------------------------------------------

select distinct(SoldAsVacant), count(SoldAsVacant)
from PortfolioProject.dbo.NashvilleHousing
Group by SoldAsVacant
order by 2

select SoldAsVacant,
case when SoldAsVacant='Y' then 'Yes'
	when SoldAsVacant='N' then 'No'
	Else SoldAsVacant
	End
from PortfolioProject.dbo.NashvilleHousing

update NashvilleHousing
set SoldAsVacant=case when SoldAsVacant='Y' then 'Yes'
	when SoldAsVacant='N' then 'No'
	Else SoldAsVacant
	End
from PortfolioProject.dbo.NashvilleHousing

/*Removing Duplicates*/------------------------------------------------------------------------------------------------------------------------


with RowNumCte as (
Select *,	
ROW_NUMBER() over(
	partition by ParcelID,
				 PropertyAddress,
				 SaleDate,
				 SalePrice,
				 LegalReference
				 order by
					UniqueID
					)row_num

from PortfolioProject.dbo.NashvilleHousing
)
delete 
from RowNumCte
where row_num > 1

/*Delete unused columns*/---------------------------------------------------------------------------------------------------------------------

alter table PortfolioProject.dbo.NashvilleHousing
drop column SaleDate,OwnerAddress,TaxDistrict,PropertyAddress

select *
from PortfolioProject.dbo.NashvilleHousing